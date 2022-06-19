package com.example.ygm.DB;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.ygm.Cart.CartListItem;
import com.example.ygm.Favorite.FavoriteListItem;
import com.example.ygm.User;
import com.example.ygm.Home.EventBannerItem;
import com.example.ygm.Home.EventItem;
import com.example.ygm.Home.HomeRankingGridItem;
import com.example.ygm.Home.PushAlarmItem;
import com.example.ygm.LogIn.UserItem;
import com.example.ygm.MyMenu.Inquiry.OneToOneInquiryItem;
import com.example.ygm.MyMenu.Inquiry.ProductInquiryItem;
import com.example.ygm.MyMenu.OrderList.OrderListItem;
import com.example.ygm.MyMenu.ServiceCenter.FAQItem;
import com.example.ygm.MyMenu.ServiceCenter.MembershipItem;
import com.example.ygm.MyMenu.ServiceCenter.NoticeItem;
import com.example.ygm.Home.RankingListItem;
import com.example.ygm.Product.CommentItem;
import com.example.ygm.Product.InquireItem;
import com.example.ygm.Product.OptionItem;
import com.example.ygm.Product.ProductItem;
import com.example.ygm.Product.ReviewItem;
import com.example.ygm.ProductList.ProductListItem;
import com.example.ygm.R;
import com.example.ygm.Search.SearchResultItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DB extends AsyncTask<String, Void, String> {

    ProgressDialog progressDialog;
    String errorString = null;
    boolean result2 = false;
    private OnResultListener listener;
    Activity tmp;
    ArrayList<String> input;
    ArrayList output;
    String mJsonString;
    int id;

    public DB(OnResultListener listener, Activity activity, int id) {
        this.listener = listener;
        tmp = activity;
        this.id = id;
        input = new ArrayList<>();
    }

    public DB(OnResultListener listener, Activity activity, int id, ArrayList output) {
        this.listener = listener;
        tmp = activity;
        this.id = id;
        this.output = output;
        input = new ArrayList<>();
    }

    public interface OnResultListener {
        void onComplete(boolean result);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(tmp, "Please Wait", "Please Wait", true, true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        progressDialog.dismiss();
        mJsonString = result;
        check(id);
        result2 = false;
        listener.onComplete(result2);
    }

    @Override
    protected String doInBackground(String... params) {
        String serverURL = params[0];
        String postParameters = GetPost(id, params);

        try {
            URL url = new URL(serverURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(50000);
            httpURLConnection.setConnectTimeout(50000);
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.connect();

            OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(postParameters.getBytes("UTF-8"));
            outputStream.flush();
            outputStream.close();

            int responseStatusCode = httpURLConnection.getResponseCode();
            Log.d("", "response code - " + responseStatusCode);

            InputStream inputStream;
            if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
            } else {
                inputStream = httpURLConnection.getErrorStream();
            }

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();

            return sb.toString().trim();
        } catch (Exception e) {
            Log.d("", "InsertData: Error ", e);
            errorString = e.toString();
            return null;
        }
    }

    public void check(int id) {                              //출력함수 작성
        switch (id) {
            case R.id.login_fragment_log_in_home:
                Login();
                break;
            case R.id.pw_fragment_log_in_home:
                GetUser();
                break;
            case R.id.id_fragment_registration:
                CheckID();
                break;
            case R.id.email_fragment_registration:
                CheckMail();
                break;
            case R.id.phone_fragment_registration:
            case R.id.button_authentication:
                CheckPhone();
                break;
            case R.id.check_phoneNum_ID:
                FindIDP();
                break;
            case R.id.check_email_ID:
                FindIDE();
                break;
            case R.id.check_phoneNum_PW:
                CheckPWP();
                break;
            case R.id.check_email_PW:
                CheckPWE();
                break;
            case R.id.viewPager_event:
                GetEvent();
                break;
            case R.id.fragment_event:
                ShowEvent();
                break;
            case R.id.viewPager_home_ranking:
                ShowRank();
                break;
            case R.id.recyclerView_rankinglist:
                ShowRankALL();
                break;
            case R.id.recyclerView_cartlist:
                ShowCart();
                break;
            case R.id.button_all_orderlist_mymenu:
                ShowBuylist();
                break;
            case R.id.button_product_inquiry:
                ShowUserProductQ();
                break;
            case R.id.button_onetoone_inquiry:
                ShowQnA();
                break;
            case R.id.button_notice:
                ShowNotice();
                break;
            case R.id.button_faq:
                ShowFAQ();
                break;
            case R.id.button_membership:
                ShowBenefit();
                break;
            case R.id.Count_product_list:
                ShowALLCategory();
                break;
            case R.id.categoryFragment:
                ShowFirstALL();
                break;
            case R.layout.fragment_product_list:
                ShowPListALL();
                break;
            case R.id.count_fragment_search_result:
                Search();
                break;
            case R.id.search_fragment_search:
                RecentSearch();
                break;
            case R.id.Average_rating_Product:
                ShowProduct1();
                break;
            case R.id.contents_product:
                ShowProduct2();
                break;
            case R.id.price_product:
                ShowProduct3();
                break;
            case R.id.review_product:
                ShowProduct4();
                break;
            case R.id.Count_review_fragment_product:
                ShowProduct5();
                break;
            case R.id.recyclerView_list_review:
                ShowProduct6();
                break;
            case R.id.recyclerView_favorite:
                ShowLike();
                break;
            case R.id.button8:
                InsertCart1();
                break;
            case R.id.recyclerView_push_alarm:
                ShowPushAlarm();
                break;
        }
    }

    public String GetPost(int id, String... params) {             // 입력 부분 switch로 입력 매개변수 개수별로 분류
        String postParameters = null;
        switch (id) {
            case R.id.pw_fragment_log_in_home:     // GetUser.php
            case R.id.button_login_logout:     // GetUser.php
            case R.id.id_fragment_registration:     // CheckID.php
            case R.id.email_fragment_registration:     // CheckMail.php
            case R.id.phone_fragment_registration:     // CheckPhone.php
            case R.id.button_authentication:        // CheckPhone.php
            case R.id.check_email_ID:     // FindIDE.php
            case R.id.fragment_event:               // ShowEvent.php
            case R.id.recyclerView_cartlist:        // ShowCart.php
            case R.id.imageButton_plus:             // PlusCart.php
            case R.id.imageButton_minus:            // MinusCart.php
            case R.id.imageButton_remove:           // DeleteCart.php
            case R.id.button_all_orderlist_mymenu:    // ShowBuylist.php
            case R.id.recyclerView_list_review:   // ShowProduct6.php
            case R.layout.fragment_product_list:    // ShowPListALL.php
            case R.id.search_fragment_search:    // RecentSearch.php
            case R.id.recyclerView_favorite:        // ShowLike.php
            case R.id.button_product_inquiry:       // ShowUserProductQ.php
            case R.id.button_onetoone_inquiry:      // ShowQnA.php
            case R.id.switch_push_alarm_setting:    // PushOn.php, PushOff.php
            case R.id.button_withdrawal:    // RemoveUser.php
                input.add(0, params[1]);
                postParameters = "A=" + input.get(0);     // 매개변수 1개인 경우
                break;
            case R.id.login_fragment_log_in_home:    // Login.php
            case R.id.check_email_PW:    // CheckPWE.php
            case R.id.check_phoneNum_ID:    // FindIDP.php
            case R.id.find_pw_fragment_find_pw:    // ResetPw.php
            case R.id.button_modify_pw_complete:    // ResetPW.php
            case R.id.count_fragment_search_result:    // Search.php
            case R.id.Average_rating_Product:    // ShowProduct1.php
            case R.id.contents_product:    // ShowProduct2.php
            case R.id.price_product:    // ShowProduct3.php
            case R.id.review_product:    // ShowProduct4.php
            case R.id.Count_review_fragment_product: // ShowProduct5.php
            case R.id.imageButton:    // PlusLike.php
            case R.id.payment_and_cart_product:    // MinusLike.php
            case R.id.button_modify_phone_complete:    // UpdatePhone.php
            case R.id.Count_product_list:    // ShowALLCategory.php
            case R.id.listView_search:      // InsertRecentsearch.php
                input.add(0, params[1]);
                input.add(1, params[2]);
                postParameters = "A=" + input.get(0) + "&B=" + input.get(1);        // 매개변수 2개인 경우
                break;
            case R.id.check_phoneNum_PW:    // CheckPWP.php
            case R.id.button8:    // InsetCart1.php
            case R.id.button10:    // InsertCart2.php
            case R.id.fragment_modify_height_weight:    // UpdateBody.php
                for (int i = 0; i < 3; i++)
                    input.add(i, params[i + 1]); // A to C
                postParameters = "A=" + input.get(0) + "&B=" + input.get(1) + "&C=" + input.get(2);         // 매개변수 3개인 경우
                break;
            case R.layout.activity_one_to_one_inquire: // InsertQnA.php
            case R.id.write_comment_review_product:    //InsertComment.php RID,id,level,comment
                for (int i = 0; i < 4; i++)
                    input.add(i, params[i + 1]); // A to D
                postParameters = "A=" + input.get(0) + "&B=" + input.get(1) + "&C=" + input.get(2) + "&D=" + input.get(3);
                break;
            case R.id.fragment_registration:    // Registration.php
            case R.id.image_product_inquire:    // InsertProductQ.php
                for (int i = 0; i < 6; i++)
                    input.add(i, params[i + 1]); // A to F
                postParameters = "A=" + input.get(0) + "&B=" + input.get(1) + "&C=" + input.get(2) + "&D=" + input.get(3) + "&E=" + input.get(4);
                postParameters += "&F=" + input.get(5);
                break;
            case R.id.button_register_write_review:    // InsertReviewNoimage.php
            case 100:    // Buy.php
                for (int i = 0; i < 9; i++)
                    input.add(i, params[i + 1]); // A to I
                postParameters = "A=" + input.get(0) + "&B=" + input.get(1) + "&C=" + input.get(2) + "&D=" + input.get(3) + "&E=" + input.get(4);
                postParameters += "&F=" + input.get(5) + "&G=" + input.get(6) + "&H=" + input.get(7) + "&I=" + input.get(8);
                break;
            case R.id.textView_pay_payment:    // BuyCart.php
                for (int i = 0; i < 10; i++)
                    input.add(i, params[i + 1]); // A to J
                postParameters = "A=" + input.get(0) + "&B=" + input.get(1) + "&C=" + input.get(2) + "&D=" + input.get(3) + "&E=" + input.get(4);
                postParameters += "&F=" + input.get(5) + "&G=" + input.get(6) + "&H=" + input.get(7) + "&I=" + input.get(8) + "&J=" + input.get(9);
                break;
            default:
                return params[0];
        }
        return postParameters;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void Login() {
        String TAG_JSON = "User";
        Log.d("ppppppp", mJsonString);
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String count = item.getString("count");

                output.add(count);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void GetUser() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String UID = item.getString("UID");
                String ID = item.getString("ID");
                String PW = item.getString("PW");
                String Uname = item.getString("Uname");
                String phone = item.getString("phone");
                String email = item.getString("email");
                String birthdate = item.getString("birthdate");
                int height = Integer.parseInt(item.getString("height"));
                int weight = Integer.parseInt(item.getString("weight"));
                int exp = Integer.parseInt(item.getString("exp"));
                String push = item.getString("push");
                int point = Integer.parseInt(item.getString("point"));
                String regdate = item.getString("regdate");

                output.add(new UserItem(UID, ID, PW, Uname, phone, email, birthdate, height, weight, exp, (push.equals("1")) ? true : false, point, regdate));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void CheckID() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String count = item.getString("count");

                output.add(count);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void CheckPhone() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String count = item.getString("count");

                output.add(count);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void CheckMail() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String count = item.getString("count");
                Log.d("QQQQQQQQQQQQQ", count);

                output.add(count);
                Log.d("EEEEEEEEEEEEE", output.get(0).toString());
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void FindIDP() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String ID = item.getString("ID");
                output.add(ID);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void FindIDE() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String ID = item.getString("ID");

                output.add(ID);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void CheckPWP() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String count = item.getString("count");

                output.add(count);
            }

        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void CheckPWE() {
        String TAG_JSON = "User";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String count = item.getString("count");

                output.add(count);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void GetEvent() {
        String TAG_JSON = "Event";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String EID = item.getString("EID");
                String ssumimage = item.getString("ssumimage");

                output.add(new EventBannerItem(EID, ssumimage));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowEvent() {
        String TAG_JSON = "Event";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String Ename = item.getString("Ename");
                String mainimage = item.getString("mainimage");

                output.add(new EventItem(Ename, mainimage));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowRank() {
        String TAG_JSON = "Event";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String PID = item.getString("PID");
                // int pcount = Integer.parseInt(item.getString("pcount"));
                String thumb = item.getString("thumb");
                String Pname = item.getString("Pname");
                int price = Integer.parseInt(item.getString("price"));

                // 처음 12개가 전체, 그 다음 6개씩 카테고리별로 (상의, 하의, 신발, 모자)

                output.add(new HomeRankingGridItem(PID, thumb, Pname, price));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowRankALL() {
        String TAG_JSON = "Product";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String PID = item.getString("PID");
                String Pname = item.getString("Pname");
                String thumb = item.getString("thumb");
                int price = Integer.parseInt(item.getString("price"));
                int lcount = Integer.parseInt(item.getString("lcount"));
                String tmpRate = item.getString("star");
                float rate = (tmpRate == "null") ? 0 : Float.parseFloat(tmpRate);
                int review = Integer.parseInt(item.getString("rcount"));
                int cid = Integer.parseInt(item.getString("cid"));

                Log.d("ShowRankALL", Pname + " " + price);

                output.add(new RankingListItem(PID, Pname, thumb, price, lcount, rate, review, cid));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowCart() {
        String TAG_JSON = "Product";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String CartID = item.getString("CartID");
                String PID = item.getString("PID");
                String SID = item.getString("SID");
                String thumb = item.getString("thumb");
                String Pname = item.getString("Pname");
                String color = item.getString("color");
                String size = item.getString("size");
                int price = Integer.parseInt(item.getString("price"));
                int num = Integer.parseInt(item.getString("ocount"));
                int stock = Integer.parseInt(item.getString("scount"));

                output.add(new CartListItem(CartID, PID, SID, thumb, Pname, color + " / " + size, price, num, stock));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    // 현
    public void ShowBuylist() {
        String TAG_JSON = "Buylist";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject item = jsonArray.getJSONObject(i);

                String BID = item.getString("BID");
                String PID = item.getString("PID");
                String SID = item.getString("SID");
                String image = item.getString("thumb");
                String name = item.getString("pname");
                String color = item.getString("color");
                String size = item.getString("size");
                int amount = Integer.parseInt(item.getString("paymoney"));
                int num = Integer.parseInt(item.getString("count"));
                String date = item.getString("date");
                String address = item.getString("address");

                output.add(new OrderListItem(BID, PID, SID, image, name, color + " / " + size, amount, num, date, address));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    // 마이메뉴

    public void ShowProductQ() {
        String TAG_JSON = "ProductQ";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String PID = item.getString("PID");
                String UID = item.getString("UID");
                String title = item.getString("title");
                String question = item.getString("question");
                String answer = item.getString("answer");
                String questiondate = item.getString("questiondate");
                String answerdate = item.getString("answerdate");
                String imagepath = item.getString("imagepath");

                // output.add(EID,Ename,edate,mainimage);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowLike() {
        String TAG_JSON = "Product";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String PID = item.getString("PID");
                String img = item.getString("thumb");
                String name = item.getString("Pname");
                int price = Integer.parseInt(item.getString("price"));
                String num = item.getString("lcount");

                output.add(new FavoriteListItem(PID, img, name, price, num));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowUserProductQ() {
        String TAG_JSON = "ProductQ";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String productName = item.getString("pname");
                String body = item.getString("question");
                String inquireDate = item.getString("questiondate");
                String answer = item.getString("answer");
                String answerDate = item.getString("answerdate");
                String image = item.getString("imagepath");
                String type = item.getString("type");

                output.add(new ProductInquiryItem(((User) tmp.getApplication()).getUID(), title, productName, body, inquireDate, answer, answerDate, image, type));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowQnA() {
        String TAG_JSON = "QnA";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String body = item.getString("question");
                String inquireDate = item.getString("questiondate");
                String answer = item.getString("answer");
                String answerDate = item.getString("answerdate");
                String type = item.getString("type");

                output.add(new OneToOneInquiryItem(title, body, inquireDate, answer, answerDate, type));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowNotice() {
        String TAG_JSON = "Notice";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String body = item.getString("context");
                String date = item.getString("date");

                output.add(new NoticeItem(title, date, body));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    // 현
    public void ShowFAQ() {
        String TAG_JSON = "FAQ";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String category = item.getString("type");
                String title = item.getString("question");
                String body = item.getString("answer");

                output.add(new FAQItem(category, title, body));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowBenefit() {
        String TAG_JSON = "Benefit";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String body = item.getString("image");

                output.add(new MembershipItem(title, body));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public void ShowFirstALL() { // 기한
        String TAG_JSON = "Product";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String thumb = item.getString("thumb");

                output.add(thumb);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowPListALL() { // 기한
        String TAG_JSON = "Product";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String PID = item.getString("PID");
                String thumb = item.getString("thumb");
                String cid = item.getString("cid");
                String Pname = item.getString("Pname");
                int price = Integer.parseInt(item.getString("price"));
                int lcount = Integer.parseInt(item.getString("lcount"));
                String tmpStar = item.getString("star");
                float star = (tmpStar.equals("null")) ? 0 : Float.parseFloat(tmpStar);
                int rcount = Integer.parseInt(item.getString("rcount"));
                String date = item.getString("date");
                int pcount = Integer.parseInt(item.getString("pcount"));
                int stock = Integer.parseInt(item.getString("scount"));

                output.add(new ProductListItem(PID, thumb, cid, Pname, price, lcount, star, rcount, date, pcount, stock));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }


    public void Search() {  // 기한
        String TAG_JSON = "Product";
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String PID = item.getString("PID");
                String thumb = item.getString("thumb");
                String cid = item.getString("cid");
                String Pname = item.getString("Pname");
                int price = Integer.parseInt(item.getString("price"));
                int lcount = Integer.parseInt(item.getString("lcount"));
                int rcount = Integer.parseInt(item.getString("rcount"));
                String tmpStar = item.getString("star");
                float star = (tmpStar.equals("null")) ? 0 : Float.parseFloat(tmpStar);
                int stock = Integer.parseInt(item.getString("stock"));

                output.add(new SearchResultItem(PID, thumb, cid, Pname, price, lcount, star, rcount, stock));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void RecentSearch() {
        String TAG_JSON = "RecentSearch";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String UID = item.getString("UID");
                String search = item.getString("search");

                output.add(search);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowProduct1() {

        try {
            String TAG_JSON = "Product1";                             //count 0 = 좋아요x
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String count = item.getString("count");

                output.add(count);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowProduct2() {
        try {
            String TAG_JSON2 = "Product2";                            // Product정보
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray2 = jsonObject.getJSONArray(TAG_JSON2);

            for (int i = 0; i < jsonArray2.length(); i++) {
                JSONObject item2 = jsonArray2.getJSONObject(i);

                String PID = item2.getString("PID");
                String Pname = item2.getString("Pname");
                String price = item2.getString("price");
                String pcount = item2.getString("pcount");
                String cid = item2.getString("cid");
                String thumb = item2.getString("thumb");
                String title1 = item2.getString("title1");
                String title2 = item2.getString("title2");
                String title3 = item2.getString("title3");
                String main = item2.getString("main");
                String date = item2.getString("date");
                String lcount = item2.getString("lcount");
                String star = item2.getString("star");

                output.add(new ProductItem(PID, thumb, title1, title2, title3, Pname, price, main, star));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowProduct3() {
        try {
            String TAG_JSON3 = "Product3";                            // Stock정보 - 옵션
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray3 = jsonObject.getJSONArray(TAG_JSON3);

            for (int i = 0; i < jsonArray3.length(); i++) {
                JSONObject item3 = jsonArray3.getJSONObject(i);

                String SID = item3.getString("SID");
                String PID = item3.getString("PID");
                String color = item3.getString("color");
                String size = item3.getString("size");
                String scount = item3.getString("scount");

                output.add(new OptionItem(PID, SID, color, size, scount));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowProduct4() {
        try {
            String TAG_JSON4 = "Product4";                            // 문의정보
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray4 = jsonObject.getJSONArray(TAG_JSON4);

            for (int i = 0; i < jsonArray4.length(); i++) {
                JSONObject item4 = jsonArray4.getJSONObject(i);

                String PID = item4.getString("PID");
                String UID = item4.getString("UID");
                String title = item4.getString("title");
                String question = item4.getString("question");
                String answer = item4.getString("answer");
                String questiondate = item4.getString("questiondate");
                String answerdate = item4.getString("answerdate");
                String imagepath = item4.getString("imagepath");
                String type = item4.getString("type");
                String id = item4.getString("id");

                output.add(new InquireItem(UID, PID, title, question, type, questiondate, id, answer, answerdate, imagepath));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowProduct5() {
        try {
            String TAG_JSON5 = "Product5";                            //상품리뷰
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray5 = jsonObject.getJSONArray(TAG_JSON5);
            for (int i = 0; i < jsonArray5.length(); i++) {
                JSONObject item5 = jsonArray5.getJSONObject(i);

                String RID = item5.getString("RID");
                String PID = item5.getString("PID");
                String Pname = item5.getString("Pname");
                String thumb = item5.getString("thumb");
                String size = item5.getString("size");
                String color = item5.getString("color");
                String height = item5.getString("height");
                String weight = item5.getString("weight");
                String context = item5.getString("context");
                String reviewdate = item5.getString("date");
                String image = item5.getString("image");
                String star = item5.getString("star");
                String id = item5.getString("id");
                String level = item5.getString("level");

                output.add(new ReviewItem(PID, RID, level, id, reviewdate, Pname, size, color, height, weight, context, thumb, star, image));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowProduct6() {
        try {
            String TAG_JSON5 = "Product6";                            //댓글
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray5 = jsonObject.getJSONArray(TAG_JSON5);
            for (int i = 0; i < jsonArray5.length(); i++) {
                JSONObject item5 = jsonArray5.getJSONObject(i);

                String comment = item5.getString("comment");
                String date = item5.getString("date");
                String id = item5.getString("id");
                String level = item5.getString("level");

                output.add(new CommentItem(id, level, date, comment));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void InsertCart1() {
        String TAG_JSON = "Stock";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String SID = item.getString("SID");

                output.add(SID);
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    // 현
    public void ShowPushAlarm() {
        String TAG_JSON = "PushAlarm";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String title = item.getString("title");
                String date = item.getString("datetime");

                output.add(new PushAlarmItem(title, date));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }

    public void ShowALLCategory() {
        String TAG_JSON = "Product";

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                String PID = item.getString("PID");
                String thumb = item.getString("thumb");
                String cid = item.getString("cid");
                String Pname = item.getString("Pname");
                int price = Integer.parseInt(item.getString("price"));
                int lcount = Integer.parseInt(item.getString("lcount"));
                String tmpStar = item.getString("star");
                float star = (tmpStar.equals("null")) ? 0 : Float.parseFloat(tmpStar);
                int rcount = Integer.parseInt(item.getString("rcount"));
                String date = item.getString("date");
                int pcount = Integer.parseInt(item.getString("pcount"));
                int stock = Integer.parseInt(item.getString("scount"));

                output.add(new ProductListItem(PID, thumb, cid, Pname, price, lcount, star, rcount, date, pcount, stock));
            }
        } catch (JSONException e) {
            Log.d("", "showResult : ", e);
        }
    }
}

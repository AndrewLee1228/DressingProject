package com.dressing.dressingproject.manager;

import android.content.Context;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.DetailProductActivity;
import com.dressing.dressingproject.ui.models.CodiItems;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.ProductItems;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.VersionModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 15. 11. 3.
 */
public class NetworkManager {

    private static NetworkManager instance;
    public static NetworkManager getInstance() {
        if (instance == null) {
            instance = new NetworkManager();
        }
        return instance;
    }

    AsyncHttpClient client;
    private NetworkManager() {

//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//            MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
//            socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            client = new AsyncHttpClient();
//            client.setSSLSocketFactory(socketFactory);
//            client.setCookieStore(new PersistentCookieStore(ApplicationLoader.getContext()));
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (UnrecoverableKeyException e) {
//            e.printStackTrace();
//        }
//
//
//        client.setCookieStore(new PersistentCookieStore(ApplicationLoader.getContext()));
    }

    public HttpClient getHttpClient() {
        return client.getHttpClient();
    }

    public interface OnResultListener<T> {
        public void onSuccess(T result);
        public void onFail(int code);
    }

    //서버주소
    private static final String SERVER = "http://openapi.naver.com";


    //상세코디 요청
    private static final String DETAIL_CODI_URL = SERVER + "/search";

    public void getNetworkDetailCodi(Context context, final OnResultListener<CodiItems> codiItemsOnResultListener) {
        final RequestParams params = new RequestParams();

        client.get(DETAIL_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                codiItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                CodiItems codiItems = getCodiItemsList();
                codiItemsOnResultListener.onSuccess(codiItems);
            }
        });
    }


    //상세상품 요청
    private static final String DETAIL_PRODUCT_URL = SERVER + "/search";

    public void getNetworkDetailProduct(DetailProductActivity detailProductActivity, final OnResultListener<ProductItems> productItemsOnResultListener) {
        final RequestParams params = new RequestParams();

        client.get(DETAIL_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ProductItems productItems = getProductItemsList();
                productItemsOnResultListener.onSuccess(productItems);
            }
        });
    }


    /*기존에 있던거*/

    private static List<CodiModel> codiitems = new ArrayList<>();
    private static ArrayList<ProductModel> codiData = new ArrayList<>();
    private static ArrayList<CodiModel> productData = new ArrayList<>();
    private static List<String> list = new ArrayList<String>();

    static {
        for (int i = 1; i <= 10; i++) {
            String title = "남자들아, 겨울이 오면\n이렇게 입어주자!";
            String imageURL= Integer.toString(R.drawable.test_codi);
            String estimationScore="3.5";
            String userScore ="2.0";
            boolean isFavorite=false;
            codiitems.add(new CodiModel(title,imageURL,estimationScore,userScore,isFavorite));
        }
        int[] ids = {R.drawable.test_codi2_1,R.drawable.test_codi2_2,R.drawable.test_codi2_3,R.drawable.test_codi2_4};
        for (int i = 1; i <= ids.length; i++) {
            String productTitle = "시리즈나인 도트니트";
            String produtcName ="product "+ i;
            String productBrandName="test";
            String productPrice="40000";
            String productLocation="test";
            String productImgURL= Integer.toString(ids[i-1]);
            String mapURL="test";
            String productNum ="a00000";
            boolean isFavorite=false;
            codiData.add(new ProductModel(productTitle,produtcName,productBrandName,productPrice,productLocation,productImgURL,mapURL,productNum,isFavorite));
        }
        int[] productIds = {R.drawable.test_codi,R.drawable.test_codi2,R.drawable.test_codi,R.drawable.test_codi2};
        for (int i = 1; i <= productIds.length; i++) {
            String title = "남자들아, 겨울이 오면\n이렇게 입어주자!";
            String imageURL= Integer.toString(productIds[i-1]);
            String estimationScore="3.5";
            String userScore ="";
            if (i%2 ==0) {
                userScore ="0.0";
            }
            else
                userScore ="3.5";

            boolean isFavorite=false;
            productData.add(new CodiModel(title,imageURL,estimationScore,userScore,isFavorite));
        }


        for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.data[i]);
        }
    }

    public static List<CodiModel> getRecommendList() {
        return codiitems;
    }

    public static ProductItems getProductItemsList() {
        ProductItems productItems = new ProductItems();
        productItems.items = productData;
        return productItems;
    }

    public static CodiItems getCodiItemsList() {
        CodiItems codiItems = new CodiItems();
        codiItems.items = codiData;
        return codiItems;
    }

    public static List<String> getList() {
        return list;
    }

    /*기존에 있던거*/

    public void cancelAll(Context context) {
        client.cancelRequests(context, true);
    }
}

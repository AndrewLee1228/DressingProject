package com.dressing.dressingproject.manager;

import android.content.Context;
import android.widget.Toast;

import com.dressing.dressingproject.R;
import com.dressing.dressingproject.ui.models.CodiFavoriteResult;
import com.dressing.dressingproject.ui.models.CodiModel;
import com.dressing.dressingproject.ui.models.CodiResult;
import com.dressing.dressingproject.ui.models.CodiScoreResult;
import com.dressing.dressingproject.ui.models.FavoriteCodiResult;
import com.dressing.dressingproject.ui.models.FavoriteProductResult;
import com.dressing.dressingproject.ui.models.FitResult;
import com.dressing.dressingproject.ui.models.ProductFavoriteResult;
import com.dressing.dressingproject.ui.models.ProductModel;
import com.dressing.dressingproject.ui.models.ProductResult;
import com.dressing.dressingproject.ui.models.RecommendCodiResult;
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
//            KeyStore trustStore = KeyStore.newInstance(KeyStore.getDefaultType());
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

    public void getNetworkDetailCodi(Context context, final OnResultListener<CodiResult> codiItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context,DETAIL_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                codiItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                CodiResult codiItems = getCodiItemsList();
                codiItemsOnResultListener.onSuccess(codiItems);
            }
        });
    }


    //상세상품 요청
    private static final String DETAIL_PRODUCT_URL = SERVER + "/search";

    public void getNetworkDetailProduct(Context context, final OnResultListener<ProductResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, DETAIL_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                ProductResult productResult = getProductItemsList();
                productItemsOnResultListener.onSuccess(productResult);
            }
        });
    }

    //코디 Score 요청
    private static final String CODI_SCORE_URL = SERVER + "/search";

    public void requestUpdateCodiScore(final Context context, final float rating,final CodiModel codiModel, final OnResultListener<CodiScoreResult> requestCodiScoreOnResultListener) {
        RequestParams params = new RequestParams();
        params.put("rate", rating);
        params.put("title", codiModel.getTitle());

        client.get(context,CODI_SCORE_URL,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                requestCodiScoreOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // statusCode는 네트워크 연결에대한 체크를 할 수 있고
                // 서버쪽 db조회 성공여부등의 확인은 responseString을 파싱하여 알 수 있다.
                // onSuccess에 매개변수로 파싱한 객체를 넘겨주어 서버쪽 프로세스가 잘 동작하여
                // 받은 정보가 필요한 정보인지 확인을 한다.

                //네이버 무비즈 객체와 같이 result 객체를 만들어 결과상태와 결과값 객체를 가지는 놈!
                Toast.makeText(context, codiModel.getTitle()+" 평가되었습니다!", Toast.LENGTH_SHORT).show();
                CodiScoreResult codiScoreResult = new CodiScoreResult();
                codiScoreResult.setRating(rating);
                requestCodiScoreOnResultListener.onSuccess(codiScoreResult);
            }
        });
    }

    //상품 Favorite 요청
    private static final String PRODUCT_FAVORITE_URL = SERVER + "/search";

    public void requestUpdateProductFavorite(final Context context,final ProductModel item, final OnResultListener<ProductFavoriteResult> productFavoriteResultOnResultListener) {

        //아이템의 Favorite 상태를 확인해서 파람에 Favorite 요청인지 해제인지 구분해서 인자를 세팅한다.
        if (item.isFavorite()) {

        }

        RequestParams params = new RequestParams();

        client.get(context,PRODUCT_FAVORITE_URL,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productFavoriteResultOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // statusCode는 네트워크 연결에대한 체크를 할 수 있고
                // 서버쪽 db조회 성공여부등의 확인은 responseString을 파싱하여 알 수 있다.
                // onSuccess에 매개변수로 파싱한 객체를 넘겨주어 서버쪽 프로세스가 잘 동작하여
                // 받은 정보가 필요한 정보인지 확인을 한다.


                ProductFavoriteResult productFavoriteResult = new ProductFavoriteResult();
                productFavoriteResult.setSelectedState(item.isFavorite());
                productFavoriteResultOnResultListener.onSuccess(productFavoriteResult);
            }
        });
    }


    //코디 Favorite 요청
    private static final String CODI_FAVORITE_URL = SERVER + "/search";

    public void requestUpdateCodiFavorite(final Context context,final CodiModel codiModel,final OnResultListener<CodiFavoriteResult> onResultListener)
    {

        //아이템의 Favorite 상태를 확인해서 파람에 Favorite 요청인지 해제인지 구분해서 인자를 세팅한다.
        if (codiModel.isFavorite()) {

        }
        RequestParams params = new RequestParams();

        client.put(context, CODI_FAVORITE_URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // statusCode는 네트워크 연결에대한 체크를 할 수 있고
                // 서버쪽 db조회 성공여부등의 확인은 responseString을 파싱하여 알 수 있다.
                // onSuccess에 매개변수로 파싱한 객체를 넘겨주어 서버쪽 프로세스가 잘 동작하여
                // 받은 정보가 필요한 정보인지 확인을 한다.


                CodiFavoriteResult codiFavoriteResult = new CodiFavoriteResult();
                codiFavoriteResult.setSelectedState(codiModel.isFavorite());
                onResultListener.onSuccess(codiFavoriteResult);
            }
        });

    }

    //추천코디리스트요청
    private static final String RECOMMEND_CODI_URL = SERVER + "/search";

    public void requestGetRecommendCodi(Context context, final OnResultListener<RecommendCodiResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, RECOMMEND_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                RecommendCodiResult recommendCodiResult = getRecommendCodiList();
                productItemsOnResultListener.onSuccess(recommendCodiResult);
            }
        });
    }

    //찜코디 리스트 요청
    //추천코디요청
    private static final String FAVORITE_CODI_URL = SERVER + "/search";

    public void requestGetFavoriteCodi(Context context, final OnResultListener<FavoriteCodiResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FAVORITE_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FavoriteCodiResult favoriteCodiResult = getFavoriteCodiList();
                productItemsOnResultListener.onSuccess(favoriteCodiResult);
            }
        });
    }
    //찜상품 리스트 요청
    private static final String FAVORITE_PODUCT_URL = SERVER + "/search";

    public void requestGetFavoriteProduct(Context context, final OnResultListener<FavoriteProductResult> productItemsOnResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FAVORITE_PODUCT_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                productItemsOnResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FavoriteProductResult favoriteCodiResult = getFavoriteProductItemsList();
                productItemsOnResultListener.onSuccess(favoriteCodiResult);
            }
        });
    }

    //코디 fit 요청
    private static final String FIT_CODI_URL = SERVER + "/search";

    public void requestGetFitCodi(Context context,final CodiModel codiModel, final OnResultListener<FitResult> onResultListener) {
        RequestParams params = new RequestParams();

        client.get(context, FIT_CODI_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FitResult fitCodiResult = new FitResult();
                fitCodiResult.setFit(codiModel.isFit());
                onResultListener.onSuccess(fitCodiResult);
            }
        });
    }

    //상품 fit 요청
    private static final String FIT_PRODUCT_URL = SERVER + "/search";

    public void requestGetFitProduct(Context context,final ProductModel productModel,final OnResultListener<FitResult> onResultListener)
    {
        RequestParams params = new RequestParams();

        client.get(context, FIT_PRODUCT_URL, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                onResultListener.onFail(statusCode);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                FitResult fitCodiResult = new FitResult();
                fitCodiResult.setFit(productModel.isFit());
                onResultListener.onSuccess(fitCodiResult);
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
            String descript = "코디설명 블라블라";
            String imageURL= Integer.toString(R.drawable.test_codi);
            String estimationScore="3.5";
            String userScore ="2.0";
            boolean isFavorite=false;
            codiitems.add(new CodiModel(title,descript,imageURL,estimationScore,userScore,isFavorite,false));
        }
        int[] ids = {R.drawable.test_codi2_1,R.drawable.test_codi2_2,R.drawable.test_codi2_3,R.drawable.test_codi2_4};
        for (int i = 1; i <= ids.length; i++) {
            String productTitle = "시리즈나인 도트니트";
            String produtcName ="product "+ i;
            String productBrandName="test";
            String productPrice="40000";
            String productLocation="test";
            String productImgURL= Integer.toString(ids[i-1]);
            String productLogoImgURL = Integer.toString(R.mipmap.ic_launcher);
            String mapURL="test";
            String productNum ="a00000";
            boolean isFavorite=false;
            boolean isFit=false;
            if (i % 2== 0) {
                isFavorite = true;
                isFit = true;
            }

            codiData.add(new ProductModel(productTitle,produtcName,productBrandName,productPrice,productLocation,productImgURL,productLogoImgURL,mapURL,productNum,isFavorite,isFit));
        }
        int[] productIds = {R.drawable.test_codi,R.drawable.test_codi2,R.drawable.test_codi,R.drawable.test_codi2};
        for (int i = 1; i <= productIds.length; i++) {
            String title = "남자들아, 겨울이 오면\n이렇게 입어주자!";
            String descript = "코디설명 블라블라";
            String imageURL= Integer.toString(productIds[i-1]);
            String estimationScore="3.5";
            String userScore ="";
            boolean isFavorite=false;
            if (i%2 ==0) {
                userScore ="0.0";
                isFavorite = true;
                estimationScore ="0.0";
            }
            else
                userScore ="3.5";


            productData.add(new CodiModel(title,descript,imageURL,estimationScore,userScore,isFavorite,false));
        }


        for (int i = 0; i < VersionModel.data.length; i++) {
            list.add(VersionModel.data[i]);
        }
    }

    public static List<CodiModel> getRecommendList() {
        return codiitems;
    }

    public static ProductResult getProductItemsList() {
        ProductResult productResult = new ProductResult();
        productResult.items = productData;
        return productResult;
    }
    public static FavoriteProductResult getFavoriteProductItemsList() {
        FavoriteProductResult favoriteProductResult = new FavoriteProductResult();
        favoriteProductResult.items = codiData;
        return favoriteProductResult;
    }

    public static RecommendCodiResult getRecommendCodiList() {
        RecommendCodiResult recommendCodiResult = new RecommendCodiResult();
        ArrayList<CodiModel> tempList = new ArrayList<CodiModel>();
        tempList.addAll(productData);
        tempList.addAll(productData);
        recommendCodiResult.items = tempList;
        return recommendCodiResult;
    }
    public static FavoriteCodiResult getFavoriteCodiList() {
        FavoriteCodiResult recommendCodiResult = new FavoriteCodiResult();
        ArrayList<CodiModel> tempList = new ArrayList<CodiModel>();
        tempList.addAll(productData);
        tempList.addAll(productData);
        recommendCodiResult.items = tempList;
        return recommendCodiResult;
    }

    public static CodiResult getCodiItemsList() {
        CodiResult codiItems = new CodiResult();
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

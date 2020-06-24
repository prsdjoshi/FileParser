package com.commodity.scrolltextviewdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
public class MainActivity extends AppCompatActivity {

    private PhotoLoader mPhotoLoader;
    private Subscription mImageLoadSubscription;
    private ImageView imageview;
    int i = 0;
    private EditText edtxt;
    Button button;
    private SpannableString spannableString;
    private TextView span_txt;
    private HashMap<String, Bitmap> bitmapsmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageview=(ImageView)findViewById(R.id.image);
        edtxt=(EditText)findViewById(R.id.ed_txt);
        button=(Button)findViewById(R.id.btn);
        span_txt=(TextView)findViewById(R.id.span_txt);
        mPhotoLoader = PhotoLoader.getInstance(this);
        ArrayList<Bitmap> bitmaps =new ArrayList<>();
        Log.d("Parsing started","started");
        spannableString = new SpannableString("");
        bitmapsmap =new HashMap<>();
        if (mImageLoadSubscription != null) {
            mImageLoadSubscription.unsubscribe();
        }

        mImageLoadSubscription = getIds()
                .flatMapIterable(ids -> ids)
                .concatMap(loadImageBitmapFromUrl())
                .compose(applySchedulers())
                .subscribe(new Subscriber<MyResult>() {
                    @Override
                    public void onNext(MyResult myResult) {
                        Log.d("Parsing started index",String.valueOf(i));
                        i++;
                        //imageview.setImageBitmap(bitmap);
                        bitmapsmap.put(myResult.getName(),myResult.getBitmap());
                        //imageview.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Parsing started index","error");
                        handleImageDownloadError(e);
                        mImageLoadSubscription.unsubscribe();
                    }

                    @Override
                    public void onCompleted() {
                        Log.d("Parsing completed","completed");
                        span_txt.setText(spannableString);
                        mImageLoadSubscription.unsubscribe();
                    }
                });


//        ArrayList<String> arrayList =craeteurllist();
//        for (int i=0;i<arrayList.size();i++)
//        {
//            int finalI = i;
//            mImageLoadSubscription = getId(arrayList.get(i))
//                    .concatMap(loadImageBitmapFromUrl())
//                    .compose(applySchedulers())
//                    .subscribe(new Subscriber<Bitmap>() {
//                        @Override
//                        public void onNext(Bitmap bitmap) {
//                            Log.d("Parsing started index",String.valueOf(finalI));
//                            //imageview.setImageBitmap(bitmap);
//                            bitmaps.add(bitmap);
//                            //imageview.setImageBitmap(bitmap);
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Log.e("Parsing started index","error");
//                            handleImageDownloadError(e);
//                            mImageLoadSubscription.unsubscribe();
//                        }
//
//                        @Override
//                        public void onCompleted() {
//
//                            span_txt.setText(spannableString);
//                            mImageLoadSubscription.unsubscribe();
//                        }
//                    });
//        }
        Log.d("Parsing completed","completed");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              imageview.setImageBitmap(bitmapsmap.get("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/David-Miller-IPL-1-647x363.jpeg?4VEyBDQSJof0ACmWaPri7LYbKjzUGsM2"));
              //  imageview.setImageBitmap(bitmaps.get(Integer.parseInt(edtxt.getText().toString())));
            }
        });
    }
    private Observable<List<String>> getIds() {
        ArrayList<String> imagelist=craeteurllist();
        return Observable.just(imagelist);
    }
    private Observable<String> getId(String s) {
        return Observable.just(s);
    }
//    public class Item {
//        String id;
//    }
//    // Replace the content of this method with yours
//    private Observable<Item> getItemObservable(String id) {
//
//        Item item = new Item();
//        item.id = id;
//        return Observable.just(item);
//    }

    private ArrayList<String> craeteurllist() {
          ArrayList<String> imagelist = new ArrayList<>();
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS34LW7-647x363.png?5Y4eRyRtTsQPV9wlIqDQwtz8DCwzWHq4");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/dybala-647x363.jpeg?VgMc75MULxMNU7GG3Qrqs7lHpLadIYVZ");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7HJRN-647x363.png?1SBDNif8rf2etZofgS2ySdiFllveQqNt");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/dhoniharbhajan-647x363.jpeg?TLkDHciCpzQPrN.Sb4MdhSTJf.8IS8Sb");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2J8HB-647x363.png?A7eibUQ4f2CzouOt4AEFK888AP0AjOg4");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS330YZ_1-647x363.png?gafe5PTLdHh1xkuPVKuKxJy3wyqRbXbu");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/saniamirza-647x363.jpeg?Q_gBXLYW8IM9.KZQkpmPw9DpWtGxW9yJ");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/caleb-647x363.jpeg?zs45J.nUvWRDtPv9VBXG59fAj4AtA9RI");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/davidroberston-770x433-647x363.jpeg?1AL_MQbBW0KCbrZkdcEOUU.s.NWOqpOy");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS35OGB_0-647x363.png?K24uizsuPBRKBxzOgvY1jftrjWNNyFPh");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS1Y4AB-647x363.png?35vYIqMkvDQJT3TaLpmrrSyIeMI60dM8");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/pjimage__3_-647x363.jpeg?KSQwrquo2w5.R4u.wgwURGMBrPhq52kh");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/PSG-647x363.png?sNQ16mYyTSiPib3372_FCq0qafg3aLax");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/viratvssmith-647x363.jpeg?GLTAh5DsP8kTFgPzh3NQ2guap6lYqb2E");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS30IP0-647x363.png?GiVBVFZ5tuRYhoeXhbevX8gzkknQRIKm");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS31T8D-647x363.png?y8AyAGyeIdLotfyVni8cPcvIW2b_N82X");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Rahane-647x363.jpeg?A_V5N1YY1kxaoRHIFNWQi2w724UEqonY");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/kumarsangakkara-647x363.jpeg?Kub5Rz7hdTjigKLdYOK8N7jR4U9BlCxh");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/David-Miller-IPL-1-647x363.jpeg?4VEyBDQSJof0ACmWaPri7LYbKjzUGsM2");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ravishastri1985audio-647x363.jpeg?bNJmuB5MoQns52FIRTpI.TlbVBIKnBKq");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2PKI5-647x363.png?S.cIOGY688hgZuhfjcbst2TEZnC3d7c.");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/venkymysore-647x363.jpeg?qi2I13HMgNWuXzuG3e0KARFCjGHt3SUW");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Brendon-647x363.jpeg?Pb1F3af4e47oeZBjCUNE7v1EBTSP31EW");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/AFP-647x363.jpeg?Z.5VNtx7U_kITZDgxErsi5AbwrTUvQxn");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/IMG_2528-647x363.jpeg?GvH0fm9d13VEIa2QQZAXUzlJ3MeQliIs");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/WhatsApp_Image_2020-05-06_at_1_2-647x363.jpeg?p4qw.i5nUN.bHxgoIvVVAo.jdHsyBS.r");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/pjimage__2_-647x363.jpeg?vvoFfKhLiERDS25gfp6Jq5B5tdF3yQaH");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/AP20121655438396-647x363.jpeg?QV.uZqsNGmydEgmB6zheW6YyybLtuiQV");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/AP20124425506344-647x363.jpeg?PClWM2u1JiT96E2K4f3UgbqP_X747oay");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/virat-647x363.jpeg?7IYCabWFaGntiHk6qHABrngfwgJC7WF_");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/srk-647x363.jpeg?P16Wh.YJISKget.7C9jYOflE3VL2_sba");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS336AQ-647x363.png?LstVSgmDH.k7cmVH2STuL_pLiYFw0C.b");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/shastri-647x363.jpeg?lGlLtBRrir9Q41GwBi3zhlAzz.IdwEMT");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/lionelmessi-647x363.png?bLuu1JsbgcQj_yVKoadDrdZKDnmmelAw");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ravishastrri-647x363.jpeg?wDi3_5_9Y5Ck6bwm_aGk86uUntuykHv_");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/msd-647x363.jpeg?9KpRmFYMAMNb5EPb0jZzqraghPg3BveT");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/thumbnail_iplcollage1-647x363.jpeg?zYKnBat20KcYVWuAt3J8_SZ2fGKNZe2c");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/sakshidhoni-647x363.png?u3n7jYPjakIHQnsNhclnaY4QeLCVYoF2");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/inzy17072019-770x433-647x363.jpeg?CqrvnzaXKMdDVfJCkBsG_n8Trs5megys");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS35X8D-647x363.png?int2hXYZyr2wKU2.DosvILbMBWgXPlzC");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/pvsindhu123-647x363.jpeg?OAwvSDguyIEb4mPhwZAyfAZTFr4bnKs3");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/pjimage__1__0-647x363.jpeg?rqn1g44wDdc90AMfzpwdpWR0d0CY2Frp");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS35OGB-647x363.png?OqpFmVktvyBPxQ8AtVHCP3jQNaMPOqhB");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/benstokes-647x363.jpeg?lfQMPpXLyhiKSha5dg7w7C8NBmQkc27s");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/bajrangpunia-770x433-647x363.jpeg?M1ktQJyvc54gsTXUZj8z8_SV6jy_hP_K");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/umesh-yadav1305-450x303-647x363.jpeg?xpHgoNt0ouQR9_BvvrlfLDO6D1gQ6.FH");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/bhajji5052020-647x363.jpeg?bSjdssFQWS82iJOcZFO8cachnvsgBnuy");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ipldkash-647x363.jpeg?Pa6yBGioFoEQ8LpmVeqOocARPvKDNXXK");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7FV8O-647x363.png?3XFjciRyzCxWfUkBhZnnqQpwy01K_TT4");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Raina-647x363.jpeg?iTmKjPHLnjnc7h2GU1vyd6BJGXMdmhVo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2KJLP-647x363.png?abtZRV37mjQIhZN_YfIP4XP3_NidEoZ0");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ronaldo-647x363.png?tXoyLpMUgzxSDPPNb.Mep2pNMsbTb_Ay");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/harbhajanedentest-647x363.jpeg?Lya2rdczKa7KvOfDA05_Eq3FZ._MSh7b");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX1O0LF-647x363.png?8VZbsKIkJdiwnmDyh1YfwWBz1wfrs.Rt");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Kohli-647x363.jpeg?ZsyxQqwtC06WrHr9dn.CKPJbhyGmQo3A");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/tennis-647x363.jpeg?LU1pxaXcl5MAfa3qvxqKH2hxiJ2HbCw7");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX72XYP_0-647x363.png?YkBMPmseoZ.DpiF4tzbN.7__SgHPmsm4");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/taylorfritz-647x363.jpeg?at54vir_zDUHodnbZnvXkgbY_cEQpHpv");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/shreyas_iyer_rishabh_pant_bcci-647x363.jpeg?bWLz5ZNOwtLYXirX5_9qy00z72TRUMu7");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/warne-647x363.png?GADMJx3WuhJBqKd8Wlxzk.z0phz.opeu");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX6ZLNC-647x363.jpeg?lo3JKBSDfXI0B3Ir_QjiH0ZFeDxtJI.s");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/000_SAPA990410402160-647x363.jpeg?J6cOBiNuf9m8q5u__UrXYLNcYr2fMxfq");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/EOin_Morgan-647x363.png?ba2GMsTbgB8gbuiDgGh_UnWTTy2qpboz");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/hundred-647x363.png?RnCq6A6Y0zdMLbARwePx5To4vqG94rO4");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/EPL-647x363.png?lz7aiOydCOru_r8yeJi2lrd8Q.vjAoGo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/djoko-647x363.png?4Q8IfaD8apvJxXZT9HtJ7bG9Ry4_B7OD");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/bundesliga-647x363.png?pJ6biVHmklrZqL_PajJeXLXPLoGi.dVE");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/advani4052020-647x363.jpeg?7HJtgc3qkYKI6cwRrgJ1fqs_wXDrdtiU");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/pencat-647x363.jpeg?oKo706xnzmEupftZE8JqPAq0rpCln65V");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/paes4052020-647x363.jpeg?0Fq3xdKXrcBputvaq0NxEHBNxG1FengC");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7H6OD-647x363.png?89dxZ.9chMLv9OwUbs.0W971nu5affvz");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX3BA2I-647x363.png?duYsbAIjeHH0bYzkzyBr2UlXTQiIA7gU");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7FGLS-647x363.png?rjDp92vrzYio8Fl4IGpqGPxBqx1EUCyW");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/kookaburra-647x363.jpeg?wFEngprI18gnYLQmLTaCVJOufDKhnqzk");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTR1U5A7-647x363.jpeg?QX4zH6cnslnknSHLOVEBiXR1.4zZ6bwZ");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/footballrepresentative-647x363.jpeg?1rDLKAXCv8FHGgj.TOIuqLLnWWjqLYw5");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/liverpoolloris-647x363.jpeg?zdKkMYn5JUPztGCoEYFT2aHAaG31.Eju");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/EXK5V1LX0AAJ0QW-647x363.jpeg?lvp1ks.3UPsvdntyyzMMwwhEy6OdF0rC");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/shoaibakhtar-647x363.jpeg?FdbvAYHH1IUxvvu_bPmAyON8Z5s2ZFen");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2LAQO-647x363.png?3OHWeHyCaQB36CU5krqdHSZ3T8H95_Cg");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/murrayandy-647x363.png?K2zCa74BKL6CZ5D1DCsWpksuINT0pwx3");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Kohli-Russell-647x363.jpeg?XbbsMxZBFeXMk1y2kfjUvd5YuMXKtHA3");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/viratkohlit20i-647x363.jpeg?NiKCV.ubVlSvgrDfggUvdjYjKMqXQ3SG");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7H59R-647x363.png?nSjsdXgHh2S96fkgEP9a7KJ4bxWk9glR");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTR373U6-647x363.jpeg?YHw1I6jpACSY892am0QPniF5sGAvKrig");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX6Y110-647x363.png?rWGnr3KefM.BV89crZLXDp7n8dxIkdx8");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2L6QP-647x363.png?HFzg.kgkRST7K4sVRi88A8LKU92ZjLuq");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/warner04052020-647x363.jpeg?LtVTlzdLMmn_fDDvcQCUPierFyVdsEQy");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS3292O-647x363.png?Y0IEpv_pijYBf84OymdqA3ByheG5b4.Y");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX72XYP-647x363.png?3RJ9IzSaqvtItkA7LrQOnenCIjCyRIZa");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/mahi-story_647_091817082914__1-647x363.jpeg?vIK.HC5.gyi1D6MJ2HgGeLmcWKE9FJcY");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/saha-647x363.png?XPC44PT2igwBsspSKWp_25.C0_pgBoaT");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/pjimage__1_-647x363.jpeg?4dSNqJJc32xtRPD54FNuZFD4m4FjdUY0");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/liverpool-770x433-647x363.jpeg?wsqaoD_iy0tMuLNmXULI9Uc5f4IuhAJX");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/dehicaptailsricky-647x363.jpeg?DGKLxLe_YPk3qSm5Z505NbJ5O9F0kS9T");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ronaldkoeman-647x363.png?_OfOWtum6gX0HSZQKrtt3TIl187KKl7D");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Rohit-647x363.png?5ra3iQ87PMK3yRw7FDrnh0WtIiKO7ijD");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/narinderbatrahockey-647x363.jpeg?R4cESN_p7jJyFsxXnLRvyi74V23JxXE2");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX1PSCD-647x363.png?Ttof69KKXHkoekok.dL5Yheil4MJtmuQ");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/indiawomenshockey-647x363.jpeg?5hL6SeD20m50wvmJ3xEppcVOxyJgiZXf");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/jasonroyengland-647x363.jpeg?7dr21FeLaPFWAjyT4zjzzx5uT6AmGv4p");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/asif-647x363.jpeg?RpcIitwd3.iHWAnXxGx.jH3vVP6v0x2m");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/DQ0Q3575-647x363.jpeg?5GRnCK.ytBOm8no28vRXuFXUbdEnM.Pb");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/nadal-647x363.png?5bCFlqvrGa8RbHUQXTM3TiVGGWxGRq6S");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/VK_0-647x363.png?gB7wrYEHjz5BLI27b2c1GWDFwUpsMRco");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/SerieA-647x363.png?mtPT9i8pejRsXiI8OnTYq8oB7ewO2CGj");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/EPL-647x363.jpeg?Ge.Rm7ncGx0F52Go1.IUw4hWNyeeAUFi");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTXOYXC-647x363.jpeg?ZZAhEqgoVPbcLz80PAJjvtqMMDHwxTMo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2KIM-647x363.png?CcbC6AuzObH735VoRglSAbyxgjA7o.5S");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTR36P5O-647x363.jpeg?blIHVty.eRyj3VL_Ov4I_1otlbqs.1sH");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7GZAE-647x363.png?fCWF0arby39YyFlc3GD84wrZUMZq_mMI");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS30ZNR-647x363.png?bjYMaiQAw6XtotgZRPG1I5npilCvbzL.");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2UCK7-647x363.png?hhrvTj.vutV7aoof3omV7IIbWRvmIpgZ");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7FOK8-647x363.png?WhiSOfH9OST6O20QeEo1_gds0DRVLoYY");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX6RZ1B-647x363.png?JjraQxrFsgTEdn8TS_O8IeO0oCUSECqe");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/gowda3052020_0-647x363.jpeg?PTD2blxGze6eDr9petMRs8FivEKOQ4gt");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ambati-rayudu-suresh-raina-vs--647x363.jpeg?oK6RaH8jdGcYUNbM5k5iiB3XkcURuLNH");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/kohli03052020-647x363.jpeg?bPq5xSyWvD4721lyz2_ncSAPMFgvg_xo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX6AZK4-647x363.png?HPlV_AhcTn50sBnDT2KDi5vytdQCaNLA");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2MHTP-647x363.png?rQQjLbp0PXdnTokYAXqazlAjYEIdX5DL");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7GY7M-647x363.png?4aBpub4xLHxcT2N9hibbWfDwCkaCKmSS");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS34AKA-647x363.png?NZ.p8Q3It7HOOGlMhN1HxbbJrmLEm7uX");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Andre_Russell-Kulddep-647x363.jpeg?yvzKPcqNJcDmqUv9oBGgQHv67J5gUNLq");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS38HMZ-647x363.png?7EgZ6AkFqiZ1xb_N4.qAg4Ek.hiHM9qy");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/dhoni_chahal-647x363.png?.uHV_oM5xFFJmgKA9G0TAID9nUftSs.v");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTR27S2V-647x363.jpeg?gYcLMkWUZPyv7nnWREnMQwRhIFm4wE.P");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/sunil3052020-647x363.jpeg?MmXt_wvVqUDRRr96PfZq0.w7DOok25A7");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/MSD-647x363.jpeg?kj5n2kUoSMtgcxGSyY9ipvQmkBQ3WlGc");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTR12E0E-647x363.jpeg?lJML9_4Np1QO9J4DMZDa9ntXw.VzhMXR");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/gambhir09122018-647x363.jpeg?rotEiz_SffJNhftawSd.kdKtD4hFroLO");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ipl-647x363.png?YGm8QcKx4igr0iUqoC8MutmFhNVWWn0e");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX1MO5M-647x363.png?m.RVhT2M4Gpt55.NBvWchpwuvCKFcW48");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/jason-647x363.png?vLQIM_hOKMov4bBuVLQSr_qkYwOOxRLY");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/PHF-647x363.jpeg?q9xNj2UfW76ibUR.FkTTmDjQ6Mf9abeT");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/rohit-647x363.jpeg?rM0C3UhWt7onuElrrNG3Ab.Wcv2OmPFM");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Sassuolo-647x363.png?7t07VNXTPPI65SAzJYbkKuellf6Jn2gT");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/sports-647x363.png?5L2O9emFDkk3ixn4TxXU58DiTbUNUj4l");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/khawaja-647x363.jpeg?aPxPNxkCBS4fV9UGmCShHXB6WI9ymplM");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Parthiv_Patel-647x363.jpeg?vwd7YLjRKZCb6b0aV5HjBd_ZAq9OP__c");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/kOLN-647x363.png?WmYtVVq1l79vfqqi1_5EYWe0Ib.xYXNf");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/SHami-647x363.jpeg?B_HXqI1k1FsupchehHBbF9d59GYPMCBv");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/AT-647x363.jpeg?g0tN9XI9kq4wE96VfITBh1Cgi7QhZB8w");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ROhit_Shami-647x363.jpeg?KaHP319EQWcQ6omzAumGcOnaB5.2YlZl");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RohitSharma10112018-647x363.jpeg?G1QbcUTn_FdRx8ErmHrSEz9mO74cMu.P");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/chuni30042020_0-770x433-647x363.jpeg?k.Av0rtvXzco2scXpS0UmOW2gfRsK.gc");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Screenshot_2020-05-02_at_6.35.-647x363.png?QkJaGhUT3KJD_PNBsdadSJ7jgvEu6akj");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/2020-05-02T120719Z_1799577840__0-647x363.png?qUl9G9hSYZkNGCzIDvArj32EQkWWq9kB");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/manika_batra-647x363.jpeg?jJw98JnPkrkD_qDsu3V9MSyWbLXSi1gm");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/iocolympics-770x433-647x363.jpeg?sdr2Mrt2B7125FUEI0YUFgSOUPe2I3ZD");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/C8tRH_1XkAAB9Qk-647x363.jpeg?9V3u45xF_d7deg0eEfsaGgDzx1d7t.3z");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Roberto_Martinez-647x363.png?qPEt7F8uPD8aHkhkdCTPC0f5RBPIPLES");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/BeFunky-collage__13_-647x363.jpeg?MjF6Ru6Mhfx6v3S8XYqeyqEAP3VET.kW");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/R_Ashwin-647x363.jpeg?n9vsZnicZYMrpQMoxzhBYAlRbofnC4CW");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/tyson-647x363.png?icrM_uDIsH4sO3h3yTU0oUYCl1DjUWQU");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS3745L-647x363.png?hoqRjU1HqdJZt0HnIIYWQMyiOHxHXYtb");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/dhoniklrahul-647x363.jpeg?Fls3YuwuIIKgJQj3xhimxS3qb47Eprvf");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/US_Soccer-647x363.jpeg?_MZRLeglPFG3PTdQKeRHoekt3nk7P1RG");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/gavaskar-647x363.png?fYFH31k2Vwcky0J4NkFGWLsfFRgZPk4b");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS352FW-647x363.png?__xTvFTzTMvz3B71rWnfTbwemNhRAYgG");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS35X7J_0-647x363.png?SHiuUUFRoQbEeQQBX2EhJF74jHLyeILg");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/PANT-dhoni-647x363.png?7yjqjnt4M18SZG3DE7by5g56V8ObkV4k");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/rohitsharmaipl2019_1_0-647x363.jpeg?i7RK7zOxSzw5sxsjLlpjgXD1a.myhVgn");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/murray-647x363.png?dGiaX5aXeb4js4z6eKC4CwtRfaU7rgFY");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/sumit_sangwan-647x363.png?9uwkhpiMm0XzMk7Ut2b.vBrXQY69cSKo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Premier_league-647x363.png?LTskNn5bz6ynA3SxLzx_JYMuWx4davv7");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Archery-647x363.jpeg?Y2PLX0HRPon_fuQiEfMykznUCEo9JTET");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Kieran_Trippier-647x363.png?bqIRaxNmuKK2oWvvJf7J9IMt3UTF3FcP");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ETZXeTgWAAMymFZ-647x363.jpeg?LgC2urfwvFS5za8u6637C2_PIbj2M47Q");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Savita_Punia-647x363.jpeg?SYqZio3D9vYuMMERZxR418xB_M7XD3qY");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/AP20088717772720-647x363.jpeg?tD0Jo8Qt4WngJp6lb6rQnOHwhHvN5SER");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/2020-04-02T192313Z_1556959920_-647x363.png?ZUmF5tQkvlBz2u35Lh_2Dyl1WC6eN2Km");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Rishabh-647x363.jpeg?S34sZDPWLMEj1ug8hi.zBLQCXQgTEGQo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/ipl2020_0-770x433-647x363.jpeg?EY_wPu5S.qx1j5z32CzayOuAR2nFpXi0");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2K68C-647x363.png?7D5rlqB5bQp.FVp_4c7.hFHQaMJpHoOy");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2K62K-647x363.png?f1Xjg1CnuH8f6_7yRlylZS39uP4N.hjt");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX733EU-647x363.png?3cMW6QAmiw_r8yZMCkrVv5Ao7kSIbX_9");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTR173Y4-647x363.jpeg?QultuHWnMBJoxzinxha1YvND4gcQmuh3");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/starc-647x363.png?R_NAKkAwdK5v8lGEe6YGQTXRjnfhdjnK");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX21F0L-647x363.png?VgMYjunhpCjvbCZ97unDjoqNfcKIGVjo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/carolinamarin-647x363.jpeg?SJGT1YlKAThMb4Bhalgaxg24dVpYD.lY");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/hardik_twitter-647x363.jpeg?MB0gwkiGsItvjnJuGDkVwL3cvR2Wtur8");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/rohitsharmabirthday-647x363.jpeg?KPhLBXJD8_QgTGBVn4x75owKizIbyZIV");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Warner_Cummins_1-647x363.png?hnIx_9chmdb51.XAP7uUL3bHWTZuxY0D");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7GN1P-647x363.png?lF7gi1kgmexyqtajYpLws3PFohGCyeGM");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/viratkohlitimpaine-647x363.jpeg?Iubhby9aT.ja3VyXrC72UlD7hF8GdJh.");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/F1-647x363.png?.IEjg0ZRF_TqUzefJvtFbhRWMQBTvZUn");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/pjimage-647x363.jpeg?wCFWQljUBam6zToy4fkeSr8vSspc_FG7");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/gayl-647x363.png?J8KVOf.EmOaU.VIaEqOSEQMPdM2LSmIe");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS2U5ID-647x363.png?F_DNN4hQ1VhCpXyAy6v12Bq9QYfjoYzD");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/wanrer-647x363.png?IjaWAcBUoOfA0p1Vr2TtMAdQw.dB_vNp");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX46BKL-647x363.png?5gRlJCdS_EtXLCV3kk5CkxtR5TpdHtMo");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX3CHPM-647x363.png?2YvyUVsVqiT0U0_bYLP6DLl4oevYl5Bs");
         imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS330YZ-647x363.png?zQbWF8suFj8QWyVwRRg3_qxoBbV.tzRb");
        imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Bruno_Fernandes-647x363.png?uGqxuoX1aC4AYDmoe6H1x7EyXr40a0qZ");
        imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/wiceleb_fbstory_647_0404160215-647x363.jpeg?G5dU1NElt81BUxACY.S.kR0a7aCBRabq");
        imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS344DC-647x363.png?5tzyCtS5iO5UKu.wdD6gGPt3UVBcPk0");
        imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/Logo---IOA_571_855-647x363.jpeg?b6xdYLtwShdLhIJ79tazAdsCi2ODVxGV");
        imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTS1VQ11-647x363.png?jHDMbuhJAuh3oiplpCj8bYIDGYwcRrSH");
        imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/215276602-647x363.jpeg?EiOSxYVCIYhv93FhOSIGPq6RCwoAPN_l");
        imagelist.add("https://akm-img-a-in.tosshub.com/indiatoday/images/story/202005/RTX7HHN9-647x363.png?A0tQuDZlqt6v6G69y.hQhNgEB1ffuzjF");

        return imagelist;
    }

    @NonNull
    private Func1<String, Observable<? extends MyResult>> loadImageBitmapFromUrl() {
        return new Func1<String, Observable<? extends MyResult>>() {
            @Override
            public Observable<? extends MyResult> call(String imageUrl) {
                return mPhotoLoader.load(imageUrl);
            }
        };
    }
    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    private void handleImageDownloadError(Throwable e) {
        Snackbar.make(imageview, "Download failed brah", Snackbar.LENGTH_SHORT).show();
        e.printStackTrace();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImageLoadSubscription != null) {
            mImageLoadSubscription.unsubscribe();
        }
    }
}

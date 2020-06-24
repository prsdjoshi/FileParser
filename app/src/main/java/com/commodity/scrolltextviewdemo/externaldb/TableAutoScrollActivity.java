package com.commodity.scrolltextviewdemo.externaldb;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.commodity.scrolltextviewdemo.R;
import com.google.gson.JsonObject;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.util.LocaleUtil;
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class TableAutoScrollActivity extends AppCompatActivity implements Animation.AnimationListener {
    private int Iterator = 0;
    private int scrollMax,scrollPos;
    private HorizontalScrollView strip;
    private TableLayout tablelay;
    private TranslateAnimation animationToright,animationToLeft;
    int pos = 0;
    static final String URL_Path = "http://192.168.43.215/prasad_doc/TechCrunchcontinentalUSA_xlsx.xlsx";
    private Handler customHandler;
    private String responce="";
    private Animation mAnimation;
    private String titlecellValue = "";
    private TextView titletxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_auto_scroll);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        strip = (HorizontalScrollView)
                findViewById(R.id.horizontalScrollView1);
        titletxt = (TextView)
                findViewById(R.id.titletxt);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)titletxt.getLayoutParams();
        params.setMargins(20, 20, 20, 0);
        titletxt.setLayoutParams(params);

//        tablelay = (TableLayout)
//                findViewById(R.id.tablelay);
     //   targetXScroll = strip.getRight();
//         animationToLeft = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, -1f,
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f
//        );
//        animationToright = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, -1f,
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f,
//                Animation.RELATIVE_TO_SELF, 0f
//        );
//        animationToLeft.setDuration(4000);
//        animationToLeft.setRepeatMode(Animation.RESTART);
//        animationToLeft.setRepeatCount(Animation.INFINITE);
//        animationToLeft.setAnimationListener(this);

//        animationToright.setDuration(4000);
//        animationToright.setRepeatMode(Animation.RESTART);
//       // animationToright.setRepeatCount(Animation.INFINITE);
//        animationToright.setAnimationListener(this);

        //tablelay.setAnimation(animationToLeft);
        Log.d("Parsing start ","yes");
        //first method
              // new Parse_XMLtoJSONOperation().execute();
       //second method
          //  parse_xmltojson();

        //first method
//            Parse_CSVtoJSONTask downloadFilesTask = new Parse_CSVtoJSONTask();
//            downloadFilesTask.execute();

      //  second method
//             getObservableCSV().subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(csvCallObserver);

        getObservableExcel().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(excelCallObserver);

    }
    @SuppressLint("LongLogTag")
    public void SetDBdata(String offlineData, HorizontalScrollView lay_tabledata) {

        if(offlineData!=null && !offlineData.equalsIgnoreCase(""))
        {
            try {
                JSONObject json = new JSONObject(offlineData);
                TableLayout tl = new TableLayout(getApplicationContext());
                tl.setPadding(0,8,8,0);
                JSONArray jsonArray = json.getJSONArray("data");
                tl.setBackgroundColor(Color.parseColor("#000000"));
                tl.setLayoutParams(getTblLayoutParams());
                for (int i = 0; i < Integer.parseInt("5"); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(Iterator);
                    TableRow tr = new TableRow(getApplicationContext());
                    TableRow tr2 = new TableRow(getApplicationContext());
                  //  tr.setBackgroundColor(Color.parseColor("#d67712"));
                  //  tr2.setBackgroundColor(Color.parseColor("#d1e3ff"));
                    tr.setLayoutParams(getLayoutParams());
                    tr2.setLayoutParams(getLayoutParams());

                    for (Iterator<String> nameIter = jsonObject.keys(); nameIter.hasNext(); ) {
                    String currentDynamicKey = (String) nameIter.next();
                    // LocalLogger.LOGGER.debug("cureent key: "+currentDynamicKey);
                    if(i==0)
                    {
                        tr.addView(getTextView(0, currentDynamicKey, "#0868ad", Typeface.BOLD, "#d1e3ff",12));
                        tr2.addView(getTextView(i+1, jsonObject.getString(currentDynamicKey), "#d67712", Typeface.BOLD, "#d1e3ff",12));
                    }
                    else {
                        tr2.addView(getTextView(i+2, jsonObject.getString(currentDynamicKey), "#d67712", Typeface.BOLD, "#d1e3ff",12));
                    }
                    }
                    if(i==0)
                    {

                        tl.addView(tr, getTblLayoutParams());


                    }
                    tl.addView(tr2,getTblLayoutParams());

                    if(Iterator<jsonArray.length()&&Iterator!=jsonArray.length()-1)
                    {
                        Iterator++;
                    }else {
                        Iterator=0;
                    }

                    Log.d("Iterator : ", String.valueOf(Iterator));
                }

                lay_tabledata.removeAllViews();
                lay_tabledata.addView(tl);
                animationToLeft = new TranslateAnimation(
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, -1f,
                        Animation.RELATIVE_TO_SELF, 0f,
                        Animation.RELATIVE_TO_SELF, 0f
                );

                tl.measure(0, 0);
                int w = tl.getMeasuredWidth() - Resources.getSystem().getDisplayMetrics().widthPixels;
                Log.d("scroll content screnwidth: ", String.valueOf(Resources.getSystem().getDisplayMetrics().widthPixels));
                Log.d("scroll content width: ", String.valueOf(tl.getMeasuredWidth()));
                Log.d("scroll content animwidth: ", String.valueOf(w));
                if(tl.getMeasuredWidth()>Resources.getSystem().getDisplayMetrics().widthPixels)
                {
                    Animation mAnimation = new TranslateAnimation(0,
                            (float) -w, 0, 0);
                    int duration = (1000 * 20-3);
                    mAnimation.setDuration((long) (1000 * getAnimationDuration(tl.getMeasuredWidth(),"fassst")));
                    mAnimation.setRepeatMode(Animation.RESTART);
                    mAnimation.setRepeatCount(Animation.ABSOLUTE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            tl.setAnimation(mAnimation);
                        }
                    },2000);

                }


                //linearLayoutTableData.removeAllViews();
                // linearLayoutTableData.addView(tl);
            } catch (JSONException e) {
                Log.e("Exception in set DB data in  Externaldb media ", e.toString());
            }
        }else {
            Log.d("SetDb data contains no db data found in Externaldb media ","");

        }

    }


    private double getAnimationDuration(int viewLength,String speed) {
        try {
            double len = viewLength * 1.00;
            double equSlope;
            double offSetY;

            switch (speed) {
                case "slow":
                    equSlope = 0.012546419;
                    offSetY = 10.95286472;
                    break;
                case "fast":
                    equSlope = 0.003146419;
                    offSetY = 10.96186472;
                    break;
                default:
                    equSlope = 0.007146419;
                    offSetY = 10.96286472;
                    break;
            }

//            LocalLogger.LOGGER.info("speed : " + speed);
//            LocalLogger.LOGGER.info("duration : " + ((len * equSlope) + offSetY));
            return (len * equSlope) + offSetY;
        } catch (Exception e) {
        }
        return 10;
    }
    private TextView getTextView(int id, String title, String color, int typeface, String bgColor, int fontsize) {
        TextView tv = new TextView(getApplicationContext());
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextSize(fontsize);
        tv.setTextColor(Color.parseColor(color));
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setPadding(5,5,5,5);
     //   tv.setSingleLine(false);
        tv.setBackgroundColor(Color.parseColor(bgColor));
        tv.setLayoutParams(getTextParams());
        return tv;
    }
    @NonNull
    private TableRow.LayoutParams getTextParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.MATCH_PARENT);
        params.weight = 1;
        params.setMargins(8, 0, 0, 8);

        return params;
    }
    @NonNull
    private TableLayout.LayoutParams getLayoutParams() {
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.WRAP_CONTENT,
                0);
        params.weight = 1f;
        //params.setMargins(2, 0, 0, 2);
        return params;
    }
    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,6f);

        return params;
    }
    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams2() {
        TableLayout.LayoutParams params = new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,6f);

        return params;
    }
    Observer<String> excelCallObserver = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @SuppressLint("LongLogTag")
        @Override
        public void onNext(String resp) {
            try {
                Log.d("JSON resp excel xlsx to json ",resp);
                File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + "ExceltoJSON" + ".json");
              //  Log.d("printCVSContent array length: ", String.valueOf(result.length()));
                if (!file.exists()) {
                    try {
                        OutputStream fo = new FileOutputStream(file);
                        fo.write(resp.toString().getBytes());
                        fo.close();
                        Log.d("printCVSContent: ", "file created - "+file.getPath());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Log.d("FILE", "Il file esiste -"+titlecellValue);
                }
                Log.e("ffff",titlecellValue);
                titletxt.setText(titlecellValue);
                responce = resp;
                customHandler = new android.os.Handler();
                customHandler.postDelayed(updateTimerThread, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            //write here whaterver you want to repeat


            SetDBdata(responce,strip);
            customHandler.postDelayed(this, 20000);
        }
    };

    public Observable<String> getObservableExcel(){

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                try {
                     String resp = getExcelfromURL_apicall("http://192.168.43.215/prasad_doc/TechCrunchcontinentalUSA.csv");
                      emitter.onNext(resp);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
    public String getExcelfromURL_apicall(String url_path) {
        int REGISTRATION_TIMEOUT = 3 * 1000;
        int WAIT_TIMEOUT = 30 * 1000;
        HttpClient httpclient = new DefaultHttpClient();
        HttpParams params = httpclient.getParams();
        String content =  null;
        HttpResponse response;
        String jsnresp="";
        try {
            HttpConnectionParams.setConnectionTimeout(params, REGISTRATION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(params, WAIT_TIMEOUT);
            ConnManagerParams.setTimeout(params, WAIT_TIMEOUT);

            HttpGet httpGet = new HttpGet(url_path);
            response = httpclient.execute(httpGet);

            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                String extension = url_path.substring(url_path.lastIndexOf("."));
                if(extension.equalsIgnoreCase(".csv"))
                {
                    jsnresp = parseCSvExcel(response.getEntity().getContent());
                }
                else if(extension.equalsIgnoreCase(".xls"))
                {
                    Log.d("Parsing extension ",".xls");
                    jsnresp = parseXLXExcel(response.getEntity().getContent());
                }else {
                    Log.d("Parsing extension ",".xlsx");
                    jsnresp = parseXLSXExcel(response.getEntity().getContent());
                }
            } else{
                //Closes the connection.
                Log.w("HTTP1:",statusLine.getReasonPhrase());
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }
        } catch (ClientProtocolException e) {
            Log.w("HTTP2:",e );
            content = e.getMessage();
        } catch (IOException e) {
            Log.w("HTTP3:",e );
            content = e.getMessage();
        }catch (Exception e) {
            Log.w("HTTP4:",e );
            content = e.getMessage();
        }
        return jsnresp;
    }
    private String parseXLXExcel(InputStream fis){

        try{
            // Create a workbook using the Input Stream
            HSSFWorkbook myWorkBook = new HSSFWorkbook(fis);

            // Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

            // We now need something to iterate through the cells
            JSONArray jsonArrayt = new JSONArray();
            List<String>  columnList = new ArrayList<>();
            Iterator<Row> rowIter = mySheet.rowIterator();
            while(rowIter.hasNext()){

                HSSFRow  myRow = (HSSFRow ) rowIter.next();

                if (myRow.getRowNum() == 0) {
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    for (int cn = 0; cn < myRow.getLastCellNum(); cn++) {
                        HSSFCell myCell = (HSSFCell) myRow.getCell(cn,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String cellValue = "";
                        if(isCellEmpty(myCell))
                        {
                            cellValue = "";
                        }else {
                            if (myCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                                cellValue = myCell.getStringCellValue();
                            } else {
                                cellValue = String.valueOf(myCell.getNumericCellValue());
                            }
                        }
                        columnList.add(cellValue);

                    }
                    //  Log.d("columnList", columnList.toString());
                }else {
                    JSONObject json_element = new JSONObject();
                    Iterator<Cell> cellIter = myRow.cellIterator();
                    for (int cn = 0; cn < myRow.getLastCellNum(); cn++) {
                        HSSFCell myCell = (HSSFCell) myRow.getCell(cn,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                        String cellValue = "";
                        // Check for cell Type
                        if(isCellEmpty(myCell))
                        {
                            cellValue = "";
                        }else {
                            if(myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
                                if (DateUtil.isCellDateFormatted(myCell)) {
                                    DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ROOT);
                                    sdf.setTimeZone(LocaleUtil.getUserTimeZone());
                                    cellValue= sdf.format(myCell.getDateCellValue());
                                }
                                else {
                                    cellValue = String.valueOf(myCell.getNumericCellValue());
                                }
                            }else if(myCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){

                                cellValue = String.valueOf( myCell.getBooleanCellValue() );
                            }
                            else {
                                cellValue = myCell.getStringCellValue();
                            }
                        }
                        // Just some log information
                        //   Log.d("LOG_TAG", cellValue);
                        //  json_element.put(columnList.get(cellIter.next().getRowIndex()), rowList.get(j));
                        // Push the parsed data in the Java Object
                        // Check for cell index

                        json_element.put(indexExists(columnList,cn) ? columnList.get(cn) : "" ,cellValue);

                    }
                    jsonArrayt.put(json_element);
                }

            }

            return jsonArrayt.toString();        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    private String parseXLSXExcel(InputStream fis){

        try{
            // Create a workbook using the Input Stream
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

            // Get the first sheet from workbook
            XSSFSheet  mySheet = myWorkBook.getSheet("Sheet2");
            //List<XSSFTable> list=mySheet.getTables();
            JSONObject json = new JSONObject();

            // We now need something to iterate through the cells
            JSONArray jsonArrayt = new JSONArray();
            List<String>  columnList = new ArrayList<>();
            List<String> rowList = new ArrayList<>();
            Iterator<Row> rowIter = mySheet.rowIterator();

          //  XSSFRow  myRow = mySheet.getRow(0);

            while(rowIter.hasNext()){

                XSSFRow  myRow = (XSSFRow ) rowIter.next();

                if(myRow.getRowNum() == 4-1)
                {
                    //XSSFCell  myCell = (XSSFCell ) myRow.get
                    Iterator<Cell> cellIter = myRow.cellIterator();

                    if(cellIter.hasNext()) {
                        XSSFCell myCell = (XSSFCell) cellIter.next();
                        titlecellValue = "";
                        if(myCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                            titlecellValue = myCell.getStringCellValue();
                        }
                        json.put("title",titlecellValue);
                        Log.d("title: ",titlecellValue);
                    }
                }
                if(myRow.getRowNum()>=7)
                {
                    if (myRow.getRowNum() == 7) {
                        Iterator<Cell> cellIter = myRow.cellIterator();

                        while(cellIter.hasNext()){
                            XSSFCell  myCell = (XSSFCell ) cellIter.next();
                            String cellValue = "";
                            if(myCell.getCellType() == XSSFCell.CELL_TYPE_STRING){
                                cellValue = myCell.getStringCellValue();
                            }
                            else {
                                cellValue = String.valueOf(myCell.getNumericCellValue());
                            }
                            columnList.add(cellValue);
                        }
                        //  Log.d("columnList", columnList.toString());
                    }
                    else {

                        JSONObject json_element = new JSONObject();

//                    for (int cn = 0; cn < myRow.getLastCellNum(); cn++) {
//                        String cellValue = "";
//                        Cell myCell = myRow.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//                        CellType cell = CellType.forInt(myRow.getCell(cn).getCellType());
//                        switch(cell) {
//                            case NUMERIC:
//                                cellValue = String.valueOf(myCell.getNumericCellValue());
//                                break;
//                            case STRING:
//                                cellValue = myCell.getStringCellValue();
//                                break;
//                            case BLANK:
//                                cellVal/.ue = "";
//                                break;
//                        }
//                        json_element.put(columnList.get(myCell.getColumnIndex()),cellValue);
//                    }
                        Iterator<Cell> cellIter = myRow.cellIterator();
                        Log.d("getLastCellNum: ", String.valueOf(myRow.getLastCellNum()));

                            for (int cn = 0; cn < myRow.getLastCellNum(); cn++) {
                                Log.d("cn: ", String.valueOf(cn));
                                XSSFCell myCell = (XSSFCell) myRow.getCell(cn,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                String cellValue = "";
                                // Check for cell Type
                                if(isCellEmpty(myCell))
                                {
                                    cellValue = "";
                                }else {
                                    if(myCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
                                        if (DateUtil.isCellDateFormatted(myCell)) {
                                            DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ROOT);
                                            sdf.setTimeZone(LocaleUtil.getUserTimeZone());
                                            cellValue= sdf.format(myCell.getDateCellValue());
                                        }
                                        else {
                                            cellValue = String.valueOf(myCell.getNumericCellValue());
                                        }
                                    }else if(myCell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){

                                        cellValue = String.valueOf( myCell.getBooleanCellValue() );
                                    }
                                    else {
                                        cellValue = myCell.getStringCellValue();
                                    }
                                }

                                // Just some log information
                                //   Log.d("LOG_TAG", cellValue);
                                //  json_element.put(columnList.get(cellIter.next().getRowIndex()), rowList.get(j));
                                // Push the parsed data in the Java Object
                                // Check for cell index

                                json_element.put(indexExists(columnList,cn) ? columnList.get(cn) : "" ,cellValue);

                            }
                            jsonArrayt.put(json_element);
                    }
                }


            }
            json.put("data",jsonArrayt);
            return json.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return "";
    }
    private String parseCSvExcel(InputStream fis){

            try{
                // Create a workbook using the Input Stream
                SXSSFWorkbook myWorkBook = new SXSSFWorkbook(new XSSFWorkbook(fis));

            // Get the first sheet from workbook
            SXSSFSheet mySheet = myWorkBook.getSheet("TechCrunchcontinentalUSA");
            //List<XSSFTable> list=mySheet.getTables();
            JSONObject json = new JSONObject();

            // We now need something to iterate through the cells
            JSONArray jsonArrayt = new JSONArray();
            List<String>  columnList = new ArrayList<>();
            List<String> rowList = new ArrayList<>();
            Iterator<Row> rowIter = mySheet.rowIterator();

            //  XSSFRow  myRow = mySheet.getRow(0);

            while(rowIter.hasNext()){

                SXSSFRow myRow = (SXSSFRow ) rowIter.next();

                if(myRow.getRowNum() == 4-1)
                {
                    //XSSFCell  myCell = (XSSFCell ) myRow.get
                    Iterator<Cell> cellIter = myRow.cellIterator();

                    if(cellIter.hasNext()) {
                        SXSSFCell myCell = (SXSSFCell) cellIter.next();
                        titlecellValue = "";
                        if(myCell.getCellType() == SXSSFCell.CELL_TYPE_STRING){
                            titlecellValue = myCell.getStringCellValue();
                        }
                        json.put("title",titlecellValue);
                        Log.d("title: ",titlecellValue);
                    }
                }
                if(myRow.getRowNum()>=7)
                {
                    if (myRow.getRowNum() == 7) {
                        Iterator<Cell> cellIter = myRow.cellIterator();

                        while(cellIter.hasNext()){
                            SXSSFCell  myCell = (SXSSFCell ) cellIter.next();
                            String cellValue = "";
                            if(myCell.getCellType() == SXSSFCell.CELL_TYPE_STRING){
                                cellValue = myCell.getStringCellValue();
                            }
                            else {
                                cellValue = String.valueOf(myCell.getNumericCellValue());
                            }
                            columnList.add(cellValue);
                        }
                        //  Log.d("columnList", columnList.toString());
                    }
                    else {

                        JSONObject json_element = new JSONObject();

//                    for (int cn = 0; cn < myRow.getLastCellNum(); cn++) {
//                        String cellValue = "";
//                        Cell myCell = myRow.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
//                        CellType cell = CellType.forInt(myRow.getCell(cn).getCellType());
//                        switch(cell) {
//                            case NUMERIC:
//                                cellValue = String.valueOf(myCell.getNumericCellValue());
//                                break;
//                            case STRING:
//                                cellValue = myCell.getStringCellValue();
//                                break;
//                            case BLANK:
//                                cellVal/.ue = "";
//                                break;
//                        }
//                        json_element.put(columnList.get(myCell.getColumnIndex()),cellValue);
//                    }
                        Iterator<Cell> cellIter = myRow.cellIterator();
                        Log.d("getLastCellNum: ", String.valueOf(myRow.getLastCellNum()));

                        for (int cn = 0; cn < myRow.getLastCellNum(); cn++) {
                            Log.d("cn: ", String.valueOf(cn));
                            SXSSFCell myCell = (SXSSFCell) myRow.getCell(cn,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String cellValue = "";
                            // Check for cell Type
                            if(isCellEmpty(myCell))
                            {
                                cellValue = "";
                            }else {
                                if(myCell.getCellType() == SXSSFCell.CELL_TYPE_NUMERIC){
                                    if (DateUtil.isCellDateFormatted(myCell)) {
                                        DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.ROOT);
                                        sdf.setTimeZone(LocaleUtil.getUserTimeZone());
                                        cellValue= sdf.format(myCell.getDateCellValue());
                                    }
                                    else {
                                        cellValue = String.valueOf(myCell.getNumericCellValue());
                                    }
                                }else if(myCell.getCellType() == SXSSFCell.CELL_TYPE_NUMERIC){

                                    cellValue = String.valueOf( myCell.getBooleanCellValue() );
                                }
                                else {
                                    cellValue = myCell.getStringCellValue();
                                }
                            }

                            // Just some log information
                            //   Log.d("LOG_TAG", cellValue);
                            //  json_element.put(columnList.get(cellIter.next().getRowIndex()), rowList.get(j));
                            // Push the parsed data in the Java Object
                            // Check for cell index

                            json_element.put(indexExists(columnList,cn) ? columnList.get(cn) : "" ,cellValue);

                        }
                        jsonArrayt.put(json_element);
                    }
                }


            }
            json.put("data",jsonArrayt);
            return json.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }


        return "";
    }

    private boolean checkIfRowIsEmpty(Row row) {
        if (row == null) {
            return true;
        }
        if (row.getLastCellNum() <= 0) {
            return true;
        }
        for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
            Cell cell = row.getCell(cellNum);
            if (cell != null && cell.getCellTypeEnum() != CellType.BLANK ) {
                return false;
            }
        }
        return true;
    }
    public boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    public static boolean isCellEmpty(final Cell cell) {
        if (cell == null) { // use row.getCell(x, Row.CREATE_NULL_AS_BLANK) to avoid null cells
            return true;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
            return true;
        }

        if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().trim().isEmpty()) {
            return true;
        }

        return false;
    }
    Observer<List> csvCallObserver = new Observer<List>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(List csv) {
            try {
                JSONArray jsonArrayt = new JSONArray();
                List<String> columnList = null;
                List<String> rowList;
                Log.d("csv size: ", String.valueOf(csv.size()));
                for (int i = 0; i < csv.size(); i++) {
                    if(i>=2)
                    {
                        if (i == 2) {
                            String[] header_col = (String[]) csv.get(i);
                            columnList = new ArrayList<String>(Arrays.asList(header_col));
                        }
                        else {
                            String[] header_row = (String[]) csv.get(i);
                            rowList = new ArrayList<String>(Arrays.asList(header_row));

                            JSONObject json_element = new JSONObject();
                            for (int j = 0; j < rowList.size(); j++) {
                                try {
                                    json_element.put(columnList.get(j), rowList.get(j));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            jsonArrayt.put(json_element);
                        }
                    }
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("data",jsonArrayt);
                titletxt.setText(titlecellValue);
                responce = jsonObject.toString();
                Log.d("csv resp: ",responce);
                customHandler = new android.os.Handler();
                customHandler.postDelayed(updateTimerThread, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    public Observable<List> getObservableCSV(){

        return Observable.create(new ObservableOnSubscribe<List>() {
            @Override
            public void subscribe(ObservableEmitter<List> emitter) throws Exception {
                try {
                    List csv_list = getCSVfromURL_apicall("http://192.168.43.215/prasad_doc/TechCrunchcontinentalUSA.csv");
                    emitter.onNext(csv_list);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }
    private void parse_xmltojson() {
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                XMLParser parser = new XMLParser();
                String xml_string = parser.getXmlFromUrl("http://192.168.43.215/prasad_doc/convertcsv.xml"); // getting XML
                Log.d("XML to Json parse: ", "xml ended"+xml_string);
                JSONObject jsonObj = null;
                try {
                    jsonObj = XML.toJSONObject(xml_string);
                    jsonObj = XML.toJSONObject(xml_string);
                    String jsonPrettyPrintString = jsonObj.toString(4); // json pretty print
                    // System.out.println(jsonPrettyPrintString);
                    //Log.d("XML to Json parse: ", jsonPrettyPrintString);
                    return jsonPrettyPrintString;
                } catch (JSONException e) {
                    Log.e("JSON exception", e.getMessage());
                    e.printStackTrace();
                }
                return "";
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe((result) -> {

            JSONObject jsonObject =new JSONObject(result);
            JSONObject jsonObject1 = jsonObject.getJSONObject("root");
            JSONArray jsonArray = jsonObject1.getJSONArray("element");

            Log.d("XML to Json parse: ", jsonArray.toString());
            Log.d("XML to Json parse: ", "ended");
        } );
    }

//    public void \()
//    {
//        File file=new File(path);
//        CSVReader csvReader=new CSVReader(activity.this);
//        List csv=csvReader.read( new FileInputStream(file));
//        if(csv.size()>0){
//            String[] header_row =(String[]) csv.get(0);
//            if(header_row.length>1){
//                String col1=header_row[0];
//                String col2=header_row[1];
//            }
//        }
//    }
    private class Parse_CSVtoJSONTask extends AsyncTask<URL, Void, JSONArray> {
    @SuppressLint("LongLogTag")
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    protected JSONArray doInBackground(URL... urls) {
            return downloadRemoteTextFileContent();
        }
        @SuppressLint("LongLogTag")
        protected void onPostExecute(JSONArray result) {
            if(result != null){
                //printCVSContent(result);

            //    File file = getFileStreamPath("CSVtoJSON" + ".json");
                File file = new File(Environment.getExternalStorageDirectory() + "/" + File.separator + "CSVtoJSON" + ".json");
                Log.d("printCVSContent array length: ", String.valueOf(result.length()));
                if (!file.exists()) {
                    try {
                        OutputStream fo = new FileOutputStream(file);
                        fo.write(result.toString().getBytes());
                        fo.close();
                        Log.d("printCVSContent: ", "file created - "+file.getPath());
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    Log.d("FILE", "Il file esiste");
                }
                Log.d("printCVSContent: ", result.toString());
        }
        }
    }
    private void printCVSContent(List<String[]> result){
        String cvsColumn = "";

        JSONArray jsonArrayt = new JSONArray();
        ArrayList<String> strings =new ArrayList<>();
        for (int i = 0; i < result.size(); i++){
            String [] rows = result.get(i);

            if(i==0)
            {
                for (int j=0;j<rows.length;j++)
                {
                    strings.add(rows[j]);
                }
                Log.d("printCVSContent: ", strings.toString());
            }
            else {
                JSONObject json_element = new JSONObject();
                for (int j = 0; j < rows.length; j++) {
                    try {
                        json_element.put(strings.get(j),rows[j]);
                        Log.d("printCVSContent: ", strings.get(j)+","+rows[j]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                jsonArrayt.put(json_element);
            }

        }

        Log.d("printCVSContent: ", jsonArrayt.toString());
    }

    @SuppressLint("LongLogTag")
    public List getCSVfromURL_apicall(String url_path)
    {
        URL mUrl = null;
        try {
            mUrl = new URL(url_path);
            Log.d("getCSVfromURL_api call : ", "url");
        } catch (MalformedURLException e) {
            Log.e("getCSVfromURL_api MalformedURLException : ", e.toString());
        }
        try {
            assert mUrl != null;
            URLConnection connection = mUrl.openConnection();
            CSVReader csvReader = new CSVReader(TableAutoScrollActivity.this);
            Log.d("getCSVfromURL_api call : ", "URLConnection");
            List csv = csvReader.read(connection.getInputStream());
            Log.d("getCSVfromURL_api call : ", "csv");
            return csv;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    private JSONArray downloadRemoteTextFileContent(){
        URL mUrl = null;
        List<String[]> csvLine = new ArrayList<>();
        JSONArray jsonArrayt = new JSONArray();
        String[] content = null;
        try {
            mUrl = new URL(URL_Path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            assert mUrl != null;
            URLConnection connection = mUrl.openConnection();
            CSVReader csvReader=new CSVReader(TableAutoScrollActivity.this);
            List csv=csvReader.read(connection.getInputStream());
            List<String> columnList = null;
            List<String> rowList;
            Log.d("csv size: ", String.valueOf(csv.size()));
            for (int i=0;i<csv.size();i++){
                if(i==0)
                {
                    String[] header_col =(String[]) csv.get(i);
                    columnList = new ArrayList<String>(Arrays.asList(header_col));
                }else {
                    String[] header_row =(String[]) csv.get(i);
                    rowList = new ArrayList<String>(Arrays.asList(header_row));

                    JSONObject json_element = new JSONObject();
                    for (int j = 0; j < rowList.size(); j++) {
                        try {
                            json_element.put(columnList.get(j),rowList.get(j));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    jsonArrayt.put(json_element);
                }

            }

//            BufferedReader br = new BufferedReader(new
//                    InputStreamReader(connection.getInputStream()));
//            String line = "";
//
//            List<String> columnList = null;
//            List<String> rowList;
//            int i=0;
//            while((line = br.readLine()) != null){
//                if(i==0)
//                {
//                    columnList = new ArrayList<String>(Arrays.asList(line.split(",")));
//                    Log.d("columnList: ", columnList.toString());
//                    i++;
//                }
//                else {
//                    rowList = new ArrayList<String>(Arrays.asList(line.split(",")));
//                    Log.d("rowList: ", rowList.toString());
//                    JSONObject json_element = new JSONObject();
//                    for (int j = 0; j < rowList.size(); j++) {
//                        try {
//                            json_element.put(columnList.get(j),rowList.get(j));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    jsonArrayt.put(json_element);
//                }
//               // csvLine.add(content);
//            }
           // br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArrayt;
    }
    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
//        if(pos==0)
//        {
//            tablelay.setAnimation(animationToright);
//            pos=1;
//        }
//        else {
//            tablelay.setAnimation(animationToLeft);
//            pos=0;
//        }

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    private class Parse_XMLtoJSONOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            XMLParser parser = new XMLParser();
            String xml_string = parser.getXmlFromUrl(URL_Path); // getting XML
            Log.d("XML to Json parse: ", "xml ended");

            return xml_string;

        }

        @Override
        protected void onPostExecute(String result) {
         //   Log.d("XML to Json parse xml: ", result);

            JSONObject jsonObj = null;
            try {
                jsonObj = XML.toJSONObject(result);
                String jsonPrettyPrintString = jsonObj.toString(4); // json pretty print
                Log.d("XML to Json parse: ", jsonPrettyPrintString);
            } catch (JSONException e) {
                Log.e("JSON exception", e.getMessage());
                e.printStackTrace();
            }


            Log.d("XML to Json parse: ", "ended");
        }

        @Override
        protected void onPreExecute() {
            Log.d("XML to Json parse: ", "started");
        }

        @Override
        protected void onProgressUpdate(Void... values) {}
    }

}


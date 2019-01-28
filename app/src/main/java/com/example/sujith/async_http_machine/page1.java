package com.example.sujith.async_http_machine;

import android.content.Context;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class page1 extends AppCompatActivity {

    AsyncHttpClient client;
    JSONObject obj1;
    JSONArray jarray;
    RequestParams params;




    ListView list_machp;

    VideoView vid_view;
    //MediaController mediac;
    ImageView img_view;


    TextView machine_idp,name_mp,type_mp,desc_mp,usagep,pricep;

    String url="http://sicsglobal.co.in/agroapp/Json/Machinerys.aspx";

    ArrayList<String> machidA;
    ArrayList<String>nameA;
    ArrayList<String>typeA;
    ArrayList<String>descA;
    ArrayList<String>usageA;
    ArrayList<String>priceA;
    ArrayList<String>videoA;
    ArrayList<String>imageA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        client=new AsyncHttpClient();
        params=new RequestParams();








        //mediac=new MediaController(getApplicationContext());

        list_machp=(ListView)findViewById(R.id.list_mach);

        machidA=new ArrayList<String>();
        nameA=new ArrayList<String>();
        typeA=new ArrayList<String>();
        descA=new ArrayList<String>();
        usageA=new ArrayList<String>();
        priceA=new ArrayList<String>();
        videoA=new ArrayList<String>();
        imageA=new ArrayList<String>();

        Log.e("innnn","outside");

        client.get(url,params,new AsyncHttpResponseHandler(){


            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);


                try
                {


                    Log.e("innnn",content);
                    obj1=new JSONObject(content);


                    jarray=obj1.getJSONArray("Machinerys");
                    for (int i=0;i<jarray.length();i++)
                    {
                        JSONObject obj2=jarray.getJSONObject(i);

                        String m_id=obj2.getString("MachineId");
                        machidA.add("\nMachine id : "+m_id);

                        String n_ma=obj2.getString("Name");
                        nameA.add("\nName :"+n_ma);

                        String t_ma=obj2.getString("Type");
                        typeA.add("\nType : "+t_ma);

                        String de_ma=obj2.getString("Description");
                        descA.add("\nDesc : "+de_ma);

                        String us=obj2.getString("UsageProcedure");
                        usageA.add("\nUsage : "+us);

                        String pri=obj2.getString("Price");
                        priceA.add("\nPrice : "+pri);

                        String vi=obj2.getString("Video");
                        videoA.add("http://sicsglobal.co.in/agroapp/Admin/VideoFiles/"+vi);

                        String im=obj2.getString("Image");
                        imageA.add(im);


                    }
                    adapt ada=new adapt();
                    list_machp.setAdapter(ada);

                }
                catch (Exception e)
                {

                }
            }
        });


    }


    class adapt extends BaseAdapter
    {

        @Override
        public int getCount() {
            return machidA.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater in=(LayoutInflater)getApplicationContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=in.inflate(R.layout.machines,null);

            machine_idp=(TextView)convertView.findViewById(R.id.machine_id);
            name_mp=(TextView)convertView.findViewById(R.id.name_m);
            type_mp=(TextView)convertView.findViewById(R.id.type_m);
            desc_mp=(TextView)convertView.findViewById(R.id.desc_m);
            usagep=(TextView)convertView.findViewById(R.id.usage_m);
            pricep=(TextView)convertView.findViewById(R.id.price_m);
            vid_view=(VideoView)convertView.findViewById(R.id.vide_m);
            img_view=(ImageView) convertView.findViewById(R.id.ima_m);


            machine_idp.setText(machidA.get(position));
            name_mp.setText(nameA.get(position));
            type_mp.setText(typeA.get(position));
            desc_mp.setText(descA.get(position));
            usagep.setText(usageA.get(position));
            pricep.setText(priceA.get(position));


            vid_view.setVideoURI(Uri.parse(videoA.get(position)));
            //vid_view.setMediaController(mediac);
            //mediac.setAnchorView(vid_view);
            vid_view.start();

            Picasso.with(getApplicationContext())
                    .load("http://sicsglobal.co.in/agroapp/Admin/VideoFiles/"+imageA.get(position))
                    .placeholder(R.mipmap.ic_launcher_error).error(R.mipmap.ic_launcher_error)
                    .into(img_view);


            //imagep.setText(imageA.get(position));

            return convertView;
        }
    }






}

package com.example.network;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import net.sourceforge.jtds.jdbc.Driver;


public class MainActivity extends Activity implements OnClickListener {
    Button button;
    EditText editText;
    TextView testTextView;
    String text = "0001";
    String StrXml="<Request>"
		    			+ "<head>"
				    		+ "<a>" +"user"+ "</a>"
				    		+ "<b>" + "state" + "</b>"
							+ "<c>" +"developer"+"</c>"
						+ "</head>"
						+ "<data>"
							+"<no Code='09'>"
								+ "<d>" + text + "</d>"	
					    		+ "<e>" + "aqar4werta" + "</e>"
					    		+ "<f>" + "20160513060601" + "</f>"
					    		+ "<g></g>"
					    		+ "<h></h>"
					    		+ "<i></i>"
					    		+ "<j>" + "" + "</j>"
					    	+ "</no>"
						+ "</data>"
					+ "</Request>";
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.d(TAG, "onCreate");
		button=(Button)findViewById(R.id.button1);
		editText=(EditText)findViewById(R.id.editText1);
		testTextView=(TextView)findViewById(R.id.testTextView);
		button.setOnClickListener(this);
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//String url = "http://192.168.8.135:3306/";
		String url = "jdbc:mysql://localhost:3306/aucton";
		String user = "root";
		String password = "123456";
		//建立数据库连接，获取连接对象 conn
		try {
			Connection conn = (Connection) DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		//生成一条SQL语句
		String sql = "insert into tb_users(username,password,sex,age)values('张三','111','男','22')";
		//Statement stmt = conn.createStatement();
    }
    
  
    
    /**
     * 上传图片到服务器
     * 
     * StrXml=<Request><head><a>57704.001</a><b>577046</b><c>2212044891112</c></head><data><no Code='09'><d>256896546252</d><e>aqar4wertaqrweatwerwer</e><f>20160513060601</f></no><no Code='09'><d>25689654625333</d><e>aqar4wertaqrweatwerwer</e><f>2016051316601</f></no>
        </data></Request>&Checking_Key=002

     * StrXml = "<Request><head><a>" +"57704.001"+ "</a><b>" +"57704"
					+ "</b><c>" +"221204489"+"</c></head><data>"+"<no Code='09'><d>256896546252</d><e>aqar4wertaqrweatwerwer</e><f>20160513060601</f><g></g><h></h><i></i><j>00</j></no>"
					+ "</data></Request>";
					
						+ acceptMsg.getAccceptMessageString() + "</d><e>"
							+ acceptMsg.getAccceptMessageMan() + "</e><f>"
							+ acceptMsg.getAcceptCurrentTime() + "</f><g>" + 0 + "</g><h>" + 0
							+ "</h><i>" + 0 + "</i><j>" + DeviceControlActivity.HYCO_WEIGHT
							+ "</j></no>";
     * 
     * @param imageFile 包含路径
     */
 
    
	public void sendUtils(String StrXml) {

		HttpUtils httpUtils = new HttpUtils(3000);
		RequestParams rp = new RequestParams();
		rp.addBodyParameter("StrXml", StrXml);
		rp.addBodyParameter("Checking_Key", "11");
		httpUtils.send(HttpMethod.POST,
				"http://117.185.79.178:8002/PDAService.asmx/RequestInvoke", rp,
				new RequestCallBack<String>() {
					@Override
			        public void onStart() {
			            testTextView.setText("conn...");
			        }
		
			        @Override
			        public void onLoading(long total, long current, boolean isUploading) {
			            if (isUploading) {
			                testTextView.setText("upload: " + current + "/" + total);
			            } else {
			                testTextView.setText("reply: " + current + "/" + total);
			            }
			        }
		
			        @Override
			        public void onSuccess(ResponseInfo<String> responseInfo) {
			            testTextView.setText("reply: " + responseInfo.result);
			        }
		
			        @Override
			        public void onFailure(HttpException error, String msg) {
			            testTextView.setText(error.getExceptionCode() + ":" + msg);
			        }
				});
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			
			Log.d(TAG, "发送");
			text=editText.getText().toString();
			sendUtils(StrXml);
		    
			
			break;

		default:
			break;
		}
		
	}
}


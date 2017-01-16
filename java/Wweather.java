import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

/**
 * 中央气象台的天气预报API
 */
public class Wweather {

	/**
	 * 获取所有中国 省份及一级城市
	 */
	public String weather() {
		String ws_url = "http://m.weather.com.cn/data5/city.xml";
		String str = "";
		try {
			URL url = new URL(ws_url);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 解决乱码问题
			StringBuffer sb = new StringBuffer();
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s + "\r\n"); // 将内容读取到StringBuffer中
			}
			br.close();
			// System.out.println(sb.toString()); 屏幕
			str = new String(sb.toString().getBytes());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}


	/**
	 * 根据传入参数得到城市天气预报信息ID, 
	 */
	public String weatherCityId(String id) {
		String ws_url = "http://m.weather.com.cn/data5/city" + id + ".xml";
		String str = "";
		try {
			URL url = new URL(ws_url);
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// 解决乱码问题
			StringBuffer sb = new StringBuffer();
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s); // 将内容读取到StringBuffer中
			}
			br.close();
			// System.out.println(sb.toString()); 屏幕
			str = new String(sb.toString().getBytes());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}

	public void writeFile(String content) {
		BufferedWriter bw = null;
		File file = new File("F:\\weatherCode.json");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		OutputStreamWriter osw;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(file));
			bw = new BufferedWriter(osw);
			bw.write(content);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		List resultList=new ArrayList();
		Wweather w = new Wweather();
		String[] strArray = w.weather().split(",");
		for (int i = 0; i < strArray.length; i++) {
			String[] strArr = strArray[i].split("\\|");
			/*
			 * try { Thread.sleep(10); } catch (InterruptedException e) { //
			 * TODO Auto-generated catch block e.printStackTrace(); }
			 */
			String[] strArray2 = w.weatherCityId(strArr[0]).split(",");
			for (int j = 0; j < strArray2.length; j++) {
				Map map = new HashMap();
				String[] strArray3 = w.weatherCityId(strArray2[j].split("\\|")[0]).split(",");
				String cityName = strArray3[0].split("\\|")[1];
				String weatherCode = w.weatherCityId(strArray3[0].split("\\|")[0]).split("\\|")[1];
				map.put("name", cityName);
				map.put("weatherCode", weatherCode);
				resultList.add(map);
				System.out.println(cityName);
				/*
				 * for(int m=0;m<strArray3.length;m++){
				 * System.out.println(strArray3[m].split("\\|")[1]+"  "+w.
				 * weatherCityId(strArray3[m].split("\\|")[0]).split("\\|")[1]);
				 * }
				 */
			}
		}
		JSONArray json = JSONArray.fromObject(resultList); 
		w.writeFile(json.toString());
		System.out.println(resultList.toString());
	}
}

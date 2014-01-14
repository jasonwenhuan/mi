package util;

import java.util.List;

public class MyThread extends Thread {
	List<String> urls;
	String id;
	String url;

//	StringBuffer errorList = new StringBuffer();
	StringBuffer successList = new StringBuffer();
	StringBuffer logList = new StringBuffer();
//	String errorFile;
	String successFile;
	String logFile;
	String result;

	public MyThread(List<String> urls) {
		this.urls = urls;
//		this.errorFile = getName() + "_error.txt";
		this.successFile = getName() + "_success.txt";
		this.logFile = getName() + "_log.txt";
	}

	@Override
	public void run() {
		try {
			for (String s : urls) {
				try {
					String[] split = s.split("\t");
					id = split[0];
					url = split[1];
					int responseCode = HttpClientUtil.ping(url);
					result = id + "\t" + url + "\t" + responseCode + "\n";
//					if (responseCode == 200) {
						successList.append(result);
//					} else {
//						errorList.append(result);
//					}
				} catch (Exception e) {
					String log = getName() + "id:" + id;
					logList.append(log);
					continue;
				}
			}

			try {
				FileUtil.write2File(successFile, successList.toString(), "UTF-8");
				System.out.println(getName() + "_write success file success");

//				FileUtil.write2File(errorFile, errorList.toString(), "UTF-8");
//				System.out.println(getName() + "_write error file success");
//				
//				FileUtil.write2File(logFile, logList.toString(), "UTF-8");
//				System.out.println(getName() + "_write log file success");
			} catch (Exception e) {
				logList.append(getName());
			}
		} catch (Exception e) {
			logList.append(e.getMessage());
		}
	}

}
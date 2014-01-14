package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpressionUtil {

    public static void main(String[] args) {

        String pattern = "^r([\\d]+).*line$";

        String str2Match = "r215971 | ramamg01 | 2012-10-09 05:37:26 +0800 (Tue, 09 Oct 2012) | 1 line";

//        if (str2Match.matches(pattern)) {
//            System.out.println(true);
//        } else {
//            System.out.println(false);
//        }

        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(str2Match);

        while(m.find()){
            System.out.println(m.group(1));
        }

//		if(args.length < 2){
//			System.out.print("Usage:\nJava TestRegular Expression characterSequence regularExpression+");
//			System.exit(0);
//		}
//
//		System.out.println("Input: \"" + args[0] + "\"");
//		for(String arg:args){
//			System.out.println("Regular expression: \"" + arg + "\"");
//			Pattern p = Pattern.compile(arg);
//			Matcher m = p.matcher(args[0]);
//			while(m.find()){
//				System.out.println(m.group(1));
//				System.out.println(m.group(2));
//				System.out.println("Match \"" + m.group() + "\" + at positions " + m.start() + "-" + (m.end() - 1));
//			}
//		}
    }
}

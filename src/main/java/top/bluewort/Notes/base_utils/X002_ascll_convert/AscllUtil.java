package top.bluewort.Notes.base_utils.X002_ascll_convert;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.regex.Pattern;

public class AscllUtil {
    public static String convertStringToHex(String str){

        char[] chars = str.toCharArray();

        StringBuffer hex = new StringBuffer();
        for(int i = 0; i < chars.length; i++){
            hex.append(Integer.toHexString((int)chars[i]));
        }

        return hex.toString();
    }

    public static String convertHexToString(String hex){

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        if(!isHexNumber(hex))
            return hex;
        //49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for( int i=0; i<hex.length()-1; i+=2 ){

            //grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            //convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            //convert the decimal to character
            sb.append((char)decimal);

            temp.append(decimal);
        }

        return sb.toString();
    }

    //十六进制
    private static boolean isHexNumber(String str){
        if(ObjectUtils.isEmpty(str)){
            return false;
        }
        boolean flag = true;
        for(int i=0;i<str.length();i++){
            char cc = str.charAt(i);
            if(cc!='0'&&cc!='1'&&cc!='2'&&cc!='3'&&cc!='4'&&cc!='5'&&cc!='6'&&cc!='7'&&cc!='8'&&cc!='9'&&cc!='A'&&cc!='B'&&cc!='C'&&
                    cc!='D'&&cc!='E'&&cc!='F'&&cc!='a'&&cc!='b'&&cc!='c'&&cc!='c'&&cc!='d'&&cc!='e'&&cc!='f'){
                flag = false;
            }
        }
        return flag;
    }

    //504F533838383834  POS88884
    public static void main(String[] args) {

        System.out.println("\n-----ASCII码转换为16进制 -----");
        String str = "POS88884";
        System.out.println("字符串: " + str);
        String hex = convertStringToHex(str);
        System.out.println("转换为16进制 : " + hex);

        System.out.println("\n***** 16进制转换为ASCII *****");
        System.out.println("Hex : " + hex);
        System.out.println("ASCII : " + convertHexToString(null));
    }

    /**
     * 校验是否 mac地址
     * @param content
     * @return
     */
    public static boolean ifMac(String content){
        String regex = "[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}";
        if(StringUtils.isNotEmpty(content) && Pattern.matches(regex,content)){
            return true;
        }
        return false;
/*        System.out.println("if:"+ Pattern.matches(regex,content));
        System.out.println("if:"+ Pattern.matches(regex,"a4:C1:38:F5:1E:FE"));
        System.out.println("if:"+ Pattern.matches(regex,"A4:w1:38:F5:1E:FE"));
        System.out.println("if:"+ Pattern.matches(regex,"A4:a1:38:F5:1E"));
        System.out.println("if:"+ Pattern.matches(regex,"A4:a1:38:F5:1E:1E:1E"));*/
    }
}

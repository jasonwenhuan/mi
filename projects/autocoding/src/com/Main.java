package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public String newfilePath = "";
	
    public static void main(String[] args){
    	Main m = new Main();
    	
    	m.init();
    	
    	while(true){
    		System.out.println("----请输入创建的文件类型(java,js,txt,html,javabean)----");
    		Scanner s = new Scanner(System.in);
    		String str = s.nextLine();
    		if(str.equalsIgnoreCase("java")){
    			m.generateJavaFile();
    		}
    		System.out.println("----创建成功，要继续创建吗y/n----");
    		Scanner aga = new Scanner(System.in);
    		String agaStr = aga.nextLine();
    		if(agaStr.equalsIgnoreCase("n")){
    			System.exit(0);
    		}
    	}
    }
    
    private void init(){
    	
    	File dic = new File("");
    	newfilePath = dic.getAbsolutePath() + "\\src\\com\\";
    }
    
    public boolean generateJavaFile(){
    	boolean isSuccess = false;
    	boolean inputRight = false;
    	System.out.println("----请输入java文件类型(main,javabean,domainbean,hibernatedao,facade,presmanager)");
    	
    	Scanner type = new Scanner(System.in);
		String typeStr = type.nextLine();
		
		
    	System.out.println("----请输入类名----");
    	while(!inputRight){
    		Scanner s = new Scanner(System.in);
    		String str = s.nextLine();
    		char firstChar = str.charAt(0);
    		if(!Character.isUpperCase(firstChar)){
    			System.out.println("----类名首字母要大写，请重新输入----");
    		}else{
    			String file = newfilePath + str + ".java";
    			File f = new File(file);
    			if(typeStr.equalsIgnoreCase("main")){
    				writeJavaCode(file);
    				System.out.println("----创建成功----");
    			}else if(typeStr.equalsIgnoreCase("javabean")){
    				writeJavaBeanCode(file);
    			}else if(typeStr.equalsIgnoreCase("hibernatedao")){
    				writeJavaHibernateDaoCode(file);
    			}else{
    				System.out.println("----输入错误----");
    			}
    			inputRight = true;
    		}
    		
    	}
    	return isSuccess;
    }
    
    public void generateJavaBean(){
    	
    }
    
    private void writeJavaCode(String path){
    	StringBuffer sb = new StringBuffer();
    	sb.append("package com;\n");
    	sb.append("\n");
    	sb.append("public class ");
    	
    	int startIndex = path.lastIndexOf("\\")+1;
    	int endIndex = path.lastIndexOf(".");
    	String fileName = path.substring(startIndex, endIndex);
    	sb.append(fileName);
    	sb.append("{");
    	sb.append("\n");
    	
    	sb.append("    public static void main(String[] args){\n");
    	sb.append("\n");
    	sb.append("    }");
    	
    	
    	sb.append("\n");
    	sb.append("}");
    	write(path, sb.toString());
    }
    
    private void writeJavaBeanCode(String path){
    	StringBuffer sb = new StringBuffer();
    	
    	System.out.println("----请输入属性和名称（Integer,id;String,name）-----");
    	Scanner s = new Scanner(System.in);
		String str = s.nextLine();
		if(str == null || str.equalsIgnoreCase("")){
			System.out.println("属性或者名称错误");
			return;
		}
		String[] attribute = str.split(";");
		
		
		sb.append("package com;\n");
    	sb.append("\n");
    	sb.append("import java.util.Date;\n");
    	sb.append("\n");
    	
    	sb.append("public class ");
    	
    	int startIndex = path.lastIndexOf("\\")+1;
    	int endIndex = path.lastIndexOf(".");
    	String fileName = path.substring(startIndex, endIndex);
    	sb.append(fileName);
    	sb.append("{");
    	sb.append("\n\n");
    	
		
		for(int i=0;i<attribute.length;i++){
			String[] arr = attribute[i].split(",");
			sb.append("    private ");
			sb.append(arr[0]);
			sb.append(" ");
			sb.append(arr[1]);
			sb.append(";");
			sb.append("\n");
		}
		
		sb.append("\n");
        sb.append("    public ");
        sb.append(fileName);
        sb.append("(){\n\n");
        sb.append("    ");
        sb.append("}");
        sb.append("\n\n");
		
		for(int i=0;i<attribute.length;i++){
			String[] arr = attribute[i].split(",");
			//get method
			sb.append("    public ");
			sb.append(arr[0]);
			sb.append(" ");
			String getMethod = "get" + toUpperCaseFirstOne(arr[1]);
			sb.append(getMethod);
			sb.append("(){\n");
			sb.append("        return ");
			sb.append(arr[1]);
			sb.append(";\n");
			sb.append("    }\n\n");
			
			//set method
			sb.append("    public void ");
			String setMethod = "set" + toUpperCaseFirstOne(arr[1]);
			sb.append(setMethod);
			sb.append("(");
			sb.append(arr[0]);
			sb.append(" ");
			sb.append(arr[1]);
			sb.append("){\n");
			sb.append("        this.");
			sb.append(arr[1]);
			sb.append(" = ");
			sb.append(arr[1]);
			sb.append(";\n");
			sb.append("    }\n\n");
		}
		sb.append("\n}");
    	write(path, sb.toString());
    }
    
    public void writeJavaHibernateDaoCode(String filePath){
        StringBuffer sb = new StringBuffer();
        
    	System.out.println("----请输入hibernate实体名称（Appoint）-----");
    	
    	Scanner s = new Scanner(System.in);
		String entity = s.nextLine();
		if(entity == null || entity.equalsIgnoreCase("")){
			System.out.println("实体名称错误");
			return;
		}
		
		sb.append("import java.sql.SQLException;\n");
		
		sb.append("import java.util.ArrayList;\n");
		sb.append("import java.util.List;\n");
		
		sb.append("\n");
		
		sb.append("import org.hibernate.HibernateException;\n");
		sb.append("import org.hibernate.Query;\n");
		sb.append("import org.springframework.orm.hibernate3.HibernateCallback;\n");
		
		sb.append("\n");
		
		sb.append("public class ");
		sb.append(entity);
		sb.append("HibernateDao extends HibernateDao<");
		sb.append(entity);
		sb.append(">{\n");
		//create method
		sb.append("    public ");
		sb.append(entity);
		sb.append(" create");
		sb.append(entity);
		sb.append("(");
		sb.append(entity);
		String entityInstance = toLowerCaseFirstOne(entity);
		sb.append(" ");
		sb.append(entityInstance);
		sb.append("){\n");
		sb.append("        return makePersistent(");
		sb.append(entityInstance);
		sb.append(");\n");
		sb.append("    }\n\n");
		
		
		//update method
		sb.append("    public void");
		sb.append(" update");
		sb.append(entity);
		sb.append("(");
		sb.append(entity);
		sb.append(" ");
		sb.append(entityInstance);
		sb.append("){\n");
		sb.append("        return updateRecord(");
		sb.append(entityInstance);
		sb.append(");\n");
		sb.append("    }\n\n");
		
		//delete
		sb.append("    public void");
		sb.append(" delete");
		sb.append(entity);
		sb.append("(");
		sb.append(entity);
		sb.append(" ");
		sb.append(entityInstance);
		sb.append("){\n");
		sb.append("        return makeTransient(");
		sb.append(entityInstance);
		sb.append(");\n");
		sb.append("    }\n\n");
		
		
		sb.append("\n");
		
		
		sb.append("}\n");
		write(filePath, sb.toString());
    }
    
    public void write(String path, String content) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path));
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					bw = null;
				}
			}
		}
	}
    
    private String toUpperCaseFirstOne(String name) {
        char[] ch = name.toCharArray();
        ch[0] = Character.toUpperCase(ch[0]);
        StringBuffer a = new StringBuffer();
        a.append(ch);
        return a.toString();
    }
    
    private String toLowerCaseFirstOne(String name) {
        char[] ch = name.toCharArray();
        ch[0] = Character.toLowerCase(ch[0]);
        StringBuffer a = new StringBuffer();
        a.append(ch);
        return a.toString();
    }
}

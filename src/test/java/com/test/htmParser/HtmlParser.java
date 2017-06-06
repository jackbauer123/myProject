package com.test.htmParser;

import java.util.ArrayList;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.junit.Test;

import com.html.parser.OpenFile;

public class HtmlParser {

	@Test
	public void htmlParserGuiLin(){
		String ENCODE="utf-8";
		ArrayList<String> arrList=new ArrayList<String>();
		String szContent = OpenFile.openFile( "G:\\img\\guiLin.html");
		try{              
			Parser parser = Parser.createParser(szContent,ENCODE);  
			
			for (NodeIterator i = parser.elements (); i.hasMoreNodes(); ) { 
				//获取姓名
				NodeFilter filterName=new HasAttributeFilter("id","ctl00_ContentPlaceHolder1_Label2");
				NodeList nodesNames = parser.extractAllNodesThatMatch(filterName);
				Node nodeName=nodesNames.elementAt(0);
				String userNames=nodeName.toPlainTextString();
				String tmp="姓名|"+userNames;
				arrList.add(tmp);
				parser.reset();
				
				//获取证件号码
				NodeFilter filterCert=new HasAttributeFilter("id","ctl00_ContentPlaceHolder1_Label3");
				NodeList nodesCerts = parser.extractAllNodesThatMatch(filterCert);
				Node nodeCert=nodesCerts.elementAt(0);
				String userCert=nodeCert.toPlainTextString();
				tmp="证件号码|"+userCert;
				arrList.add(tmp);
			
			}
		}catch( Exception e ) {         
			
		}   
		//获取公积金
		NodeFilter filter=new HasAttributeFilter("id","ctl00_ContentPlaceHolder1_GridView1");
		//取到第grabWay个数时用|分开，做为String返回到arrlist中,其grabWay的值由table的列数决定
		int grabWay1=5;		
		grabTableData(szContent,filter,0,ENCODE,arrList,grabWay1);
	} 

	
	
	@Test
	public void htmlParserLiuZhou(){
		String ENCODE="utf-8";
		ArrayList<String> arrList=new ArrayList<String>();
		
		int grabWay1=2;
		String szContent = OpenFile.openFile2( "G:\\img\\LiuZhou.html");
		//System.out.println(szContent);
		
		NodeFilter filter=new TagNameFilter("table");
		grabTableData(szContent,filter,0,ENCODE,arrList,grabWay1);
		 
	} 
	
	@Test
	public void htmlParserWuZhou(){
		String ENCODE="utf-8";
		ArrayList<String> arrList=new ArrayList<String>();
		
		//取到第grabWay个数时用|分开，做为String返回到arrlist中,其grabWay的值由table的列数决定
		int grabWay1=2;
		String szContent = OpenFile.openFile2( "G:\\img\\wuZhou.html");
		NodeFilter filter=new TagNameFilter("table");
		grabTableData(szContent,filter,1,ENCODE,arrList,grabWay1);
		 
	} 
	
	
	@Test
	public void htmlParserNanNing(){
		String ENCODE="utf-8";
		ArrayList<String> arrList=new ArrayList<String>();
		
		//取到第grabWay个数时用|分开，做为String返回到arrlist中,其grabWay的值由table的列数决定
		int grabWay1=2;
		int grabWay2=8;
		
		String szContent = OpenFile.openFile2( "G:\\img\\nanNing.html");
		//System.out.println(szContent);
		//获取公积金数据
		NodeFilter filter=new TagNameFilter("table");
		NodeFilter filterCss1=new HasAttributeFilter("bgcolor","#FDB38E");
		NodeFilter filterCss2=new HasAttributeFilter("bgcolor","#CCCCCC");
		NodeFilter filter3=new AndFilter(filter,filterCss1);
		NodeFilter filter4=new AndFilter(filter,filterCss2);
		
		grabTableData(szContent,filter3,0,ENCODE,arrList,grabWay1);
		grabTableData(szContent,filter4,1,ENCODE,arrList,grabWay2);
		  
	} 
	
	/*
	 * 
	 * 抓取html中table的数据
	 * @param-in String html,
	 * @param-in NodeFilter filter,
	 * @param-in int TableNum,
	 * @param-in String encode
	 * @param-out ArrayList resultList
	 * **/
	
	public ArrayList<String> grabTableData(String html,NodeFilter filter,int asTableNum,String encode,ArrayList<String> arrList,int grabWay){
		try {
			Parser parser = Parser.createParser(html,encode); 
			for (NodeIterator i = parser.elements (); i.hasMoreNodes(); ) {	
				NodeList nodesC = parser.extractAllNodesThatMatch(filter);
				Node nodeC = nodesC.elementAt(asTableNum);
				NodeList nodes2C = nodeC.getChildren();
				int startInt=0;
				String resultData="";
				for(int h=0;h < nodes2C.size();h++){
					Node nodeTemp =nodes2C.elementAt(h);
					if(!nodeTemp.toPlainTextString().trim().equals("")){
						NodeList node3 = nodeTemp.getChildren();
						for(int u=0;u < node3.size();u++){
							Node nodeT=node3.elementAt(u);
							if(!nodeT.toPlainTextString().trim().equals("") || ((u%2 != 0) && nodeT.toPlainTextString().trim().equals(""))){
								
								String tmpData="";
								tmpData=nodeT.toPlainTextString();
								if(startInt%grabWay==0 && startInt!= 0){
									resultData=resultData.substring(0, resultData.length()-1);
									arrList.add(resultData);
									resultData="";
								}
								resultData=resultData+tmpData+"|";
								startInt++;
							}
						}
						resultData=resultData.substring(0, resultData.length()-1);
						arrList.add(resultData);
					}
				}
				System.out.println(arrList.toString());
			}
			parser.reset();
		} catch (ParserException e) {
			e.printStackTrace();
		}
		
		return arrList;
	}
	
	
	
}

package util;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Provider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

@ManagedBean(name = "pageAnalyser")
@SessionScoped
public class PageAnalyser {
	public static String value;
	Provider gw = new Provider("长城汇款", "http://www.zhongguoremittance.com/index.php/rate");
	Provider dbs = new Provider("DBS", "http://www.dbs.com.sg/personal/rates-online/foreign-currency-foreign-exchange.page");
	Provider ocbc = new Provider("OCBC", "http://www.ocbc.com/rates/daily_price_fx.html");
	Provider uob = new Provider("UOB", "https://uniservices1.uobgroup.com/secure/online_rates/foreign_exchange_rates_against_singapore_dollar.jsp?s_pid=HOME201205_eg_quicklnk_ql7");
	Provider icbcSG = new Provider("ICBC SG", "http://www.icbc.com.cn/ICBCDynamicSite2/foreign/Singopore/YJHrateList.aspx");
	Provider bocSG = new Provider("BoC SG", "http://www.bankofchina.com/sourcedb/sgd/");
	Provider raffles = new Provider("Raffles", "http://www.raffles1.net/currency.php");
	List<Provider> result = new ArrayList<Provider>();

	public List<Provider> getResult() {
		return this.result;
	}

	public void setResult(List<Provider> result) {
		this.result = result;
	}

	public void updateAll() throws IOException {
		this.result.clear();

		analyseProvider(this.gw);
		analyseProvider(this.dbs);
		analyseProvider(this.ocbc);
		analyseProvider(this.uob);
		analyseProvider(this.bocSG);
		analyseProvider(this.icbcSG);
		analyseProvider(this.raffles);

		this.result.add(this.gw);
		this.result.add(this.dbs);
		this.result.add(this.ocbc);
		this.result.add(this.uob);
		this.result.add(this.bocSG);
		this.result.add(this.raffles);
		this.result.add(this.icbcSG);
	}

	public void analyseProvider(Provider p) throws IOException {
		Document doc = Jsoup.connect(p.getUrl()).get();
		String docStr = doc.toString();

		String saleRate = "";
		String buyRate = "";
		DecimalFormat df = new DecimalFormat("###.###");
		if (p.getName() == "长城汇款") {
			saleRate = docStr.split("人民币")[1].split("<")[0].split(";")[1];
			buyRate = "N.A";
		}
		if (p.getName() == "DBS") {
			saleRate = docStr.split("Chinese")[1].split("selling TT/OD")[1].split("<")[0].split(">")[1];
			saleRate = df.format(1.0F / Float.valueOf(saleRate).floatValue()).toString();
			buyRate = docStr.split("Chinese")[1].split("Buying TT")[1].split("<")[0].split(">")[1];
			buyRate = df.format(1.0F / Float.valueOf(buyRate).floatValue()).toString();
		}
		if (p.getName() == "OCBC") {
			saleRate = docStr.split("Chinese Yuan Offshore")[1].split("font-size:10.0pt")[1].split("<")[0].split(">")[1];
			saleRate = df.format(1.0F / (Float.valueOf(saleRate).floatValue() / 100.0F)).toString();
			buyRate = docStr.split("Chinese Yuan Offshore")[1].split("font-size:10.0pt")[2].split("<")[0].split(">")[1];
			buyRate = df.format(1.0F / (Float.valueOf(buyRate).floatValue() / 100.0F)).toString();
		}
		if (p.getName() == "UOB") {
			saleRate = docStr.split("CHINESE RENMINBI")[1].split("<td>")[2].split("<")[0];
			saleRate = df.format(100.0F / Float.valueOf(saleRate).floatValue()).toString();
			buyRate = docStr.split("CHINESE RENMINBI")[1].split("<td>")[3].split("<")[0];
			buyRate = df.format(100.0F / Float.valueOf(buyRate).floatValue()).toString();
		}
		if (p.getName() == "ICBC SG") {
			saleRate = doc.getElementsByTag("td").get(5).html().toString();
			saleRate = df.format(Float.valueOf(saleRate)).toString();
			buyRate = "N.A";
		}
		if (p.getName() == "BoC SG") {
			saleRate = docStr.split("CNY")[1].split("<td>")[2].split("<")[0];
			saleRate = df.format(1.0F / Float.valueOf(saleRate).floatValue()).toString();
			buyRate = docStr.split("CNY")[1].split("<td>")[1].split("<")[0];
			buyRate = df.format(1.0F / Float.valueOf(buyRate).floatValue()).toString();
		}
		if (p.getName() == "Raffles") {
			Elements e = doc.getElementsByTag("table").get(7).getElementsByTag("tr").get(5).getElementsByTag("td");
			saleRate = e.get(3).html();
			saleRate = df.format(100.0F / Float.valueOf(saleRate).floatValue()).toString();
			buyRate = e.get(4).html();
			buyRate = df.format(100.0F / Float.valueOf(buyRate).floatValue()).toString();
		}
		p.setSellRate(saleRate);
		p.setBuyRate(buyRate);
	}

	public Provider getGw() {
		return this.gw;
	}

	public void setGw(Provider gw) {
		this.gw = gw;
	}

	public Provider getDbs() {
		return this.dbs;
	}

	public void setDbs(Provider dbs) {
		this.dbs = dbs;
	}
}

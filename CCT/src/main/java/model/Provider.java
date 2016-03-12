package model;

public class Provider
{
  public String name;
  public String url;
  public String value;
  public String sellRate;
  public String buyRate;
  
  public Provider() {}
  
  public Provider(String name, String url)
  {
    this.name = name;
    this.url = url;
  }
  
  public String getSellRate()
  {
    return this.sellRate;
  }
  
  public void setSellRate(String sellRate)
  {
    this.sellRate = sellRate;
  }
  
  public String getBuyRate()
  {
    return this.buyRate;
  }
  
  public void setBuyRate(String buyRate)
  {
    this.buyRate = buyRate;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getUrl()
  {
    return this.url;
  }
  
  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
}

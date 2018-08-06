package bean;

public class CardInformation {
	private String cardID,balance,money,password,transferMoney;
	private int status,belong,type,pwdError=0;

	public int getBelong() {
		return belong;
	}

	public void setBelong(int belong) {
		this.belong = belong;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCardID() {
		return cardID;
	}

	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int i) {
		this.status = i;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTransferMoney() {
		return transferMoney;
	}

	public void setTransferMoney(String transferMoney) {
		this.transferMoney = transferMoney;
	}

	public int getPwdError() {
		return pwdError;
	}

	public void setPwdError(int pwdError) {
		this.pwdError = pwdError;
	}
	
}

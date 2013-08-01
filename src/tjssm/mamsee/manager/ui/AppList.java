package tjssm.mamsee.manager.ui;


class AppList {
	private String Name;
	private String PhoneNumber;
	

	public AppList(String _Name, String _PhoneNumber)
	{
		this.Name = _Name;
		this.PhoneNumber = _PhoneNumber;
	}


	public String getName() {
		return Name;
	}


	public String getNumber() {
		return PhoneNumber;
	}

 } 
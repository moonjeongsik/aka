package tjssm.mamsee.manager.ui.manage;


class DicWord {
	public String category;
	public String word;
	

	public DicWord(String m_category, String m_word)
	{
		this.category 	= m_category;
		this.word 		= m_word;
	}


	public String getName() {
		return category;
	}


	public String getNumber() {
		return word;
	}

 } 
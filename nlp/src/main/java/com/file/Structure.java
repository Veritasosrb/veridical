package com.file;

public class Structure {
	public int struct()
	{
		  nlp.frame_structure fs=new nlp.frame_structure();
	        int k = 0;
			try {
				k = fs.call();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        return k;
	}
}

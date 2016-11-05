package info.ds003.ircbot;

public class Info {
	
	public static boolean isInteger(String str) {
	    return str.matches("^-?[0-9]+(\\.[0-9]+)?$");
	}

	public static String getNick(String name)
	{
		if( name.startsWith(":") )
			name = name.substring(1);
		
		if( name.startsWith("#") )
			return name;
		else if( name.length() > 0 && name.indexOf('!') >= 0 )
		{
			name = name.split("!")[0];
			return name;
		}
		
		return name;
	}
}

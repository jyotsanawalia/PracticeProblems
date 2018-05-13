import java.util.Scanner;

class Folder{
	private String name;
	private Folder parentFolder;

	public Folder(String name, Folder parentFolder) {
		this.name = name;
		this.parentFolder = parentFolder;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Folder getParentFolder() {
		return parentFolder;
	}

	public void setParentFolder(Folder parentFolder) {
		this.parentFolder = parentFolder;
	}
}

public class PathFolder {

	public static String parsePath(String path) {
		StringBuilder str = new StringBuilder();
		Folder parentFolder = new Folder("root",null);
		for(int i = 0; i < path.length(); i++) {
			if(path.charAt(i) == '/' &&  i == 0) {
				continue;
			}
			else if(path.charAt(i) == '/') {
				// for input starting with ..
				if(str.toString().equals("..") && !parentFolder.getName().equals("root")) {
					// Moves up the hierarchy and sets the parent folder of the previous folder
					parentFolder = parentFolder.getParentFolder();
				}
				// for any string value except "..", this creates a Folder object
				else if(!str.toString().equals("..")) {
					Folder folder = new Folder(str.toString(),parentFolder);
					parentFolder = folder;
				}
				str = new StringBuilder();
			}
			else {
				str.append(path.charAt(i));
			}
		}
		Folder folder = new Folder(str.toString(),parentFolder);
		str = new StringBuilder();

		while(!folder.getName().equals("root")) {
			if(folder.getName().equals(".")) {
				folder = folder.getParentFolder();
				continue;
			}
			// Complexity of str.insert(0, str) is O(n), can be improved. I don't know
			str.insert(0,folder.getName());
			str.insert(0,"/");
			folder = folder.getParentFolder();
		}
		return str.toString();
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter the string");
		String path = scanner.next();
		// For inputs = ".", "/",".."
		if(path.equals("/") || path.equals(".") || path.equals(".."))
			System.out.println("result: root");
		else
			System.out.println("result: "+parsePath(path));
	}
}

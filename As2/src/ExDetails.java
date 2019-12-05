

public class ExDetails {
	private long id;
	private String firstName ;
	private String lastName ;
	private String eMail ;

	private static boolean isValidId(long id) {
		int mult = 1;
		long result = 0;
		while (id > 0) {
			long digit = (id % 10) * mult;
			result += digit % 10;
			result += digit / 10;
			id = id / 10;
			mult = mult == 2 ? 1 : 2;
		}
		return result % 10 == 0;
	}

	public ExDetails(long id, String firstName, String lastName, String eMail) {
		if (!isValidId(id)) 
			throw new RuntimeException(id + " is not a valid ID");
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.eMail = eMail;
	}

	public long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String geteMail() {
		return eMail;
	}

	public String toString() {
		return String.format("%d : %s %s, Email:%s", id, firstName, lastName,
				eMail);
	}

	// Put your details here (in English!)
	// Don't put a zero at the beginning of your id, and always put the L at the end (for long).
	public static ExDetails firstStudent() {
		return new ExDetails(317225993L, "vlad", "Barkanas", "vladigr1@gmail.com");
	}

	// Put the second student details here, or return null if there is only one student.
	public static ExDetails secondStudent() {
		return new ExDetails(222222226L, "eldad", "Sesami", "bentz@gmail.com");
	}
}

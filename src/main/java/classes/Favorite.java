package classes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Favorite {
	private SimpleIntegerProperty book_ID;
	private SimpleIntegerProperty fave_ID;
	private SimpleIntegerProperty fave_page;
	private SimpleStringProperty fave_text;

	@Override
	public String toString() {
		return "Favorite{" +
				"book_ID=" + book_ID +
				", fave_ID=" + fave_ID +
				", fave_page=" + fave_page +
				", fave_text=" + fave_text +
				'}';
	}

	public Favorite(int book_ID, int fave_ID, int fave_page, String fave_text) {
		this.book_ID = new SimpleIntegerProperty(book_ID);
		this.fave_ID = new SimpleIntegerProperty(fave_ID);
		this.fave_page = new SimpleIntegerProperty(fave_page);
		this.fave_text = new SimpleStringProperty(fave_text);
	}

	public int getFave_ID() {
		return fave_ID.get();
	}
	public int getBook_ID() {
		return book_ID.get();
	}
	public int getFave_Page() {
		return fave_page.get();
	}
	public String getFave_text() {
		return fave_text.get();
	}

	public IntegerProperty book_ID_property() {
		return book_ID;
	}
	public IntegerProperty fave_ID_property() {
		return fave_ID;
	}
	public IntegerProperty fave_page_property() {
		return fave_page;
	}
	public StringProperty fave_text_property() {
		return fave_text;
	}

}

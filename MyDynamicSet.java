/**
 * @param <T> The type of the satellite data of the elements in the dynamic-set.
 */
public class MyDynamicSet<T> {
	// שדה פרטי שמחזיק את המבנה נתונים האמיתי שלנו
    private MySortedArray<T> sortedArray;

	public MyDynamicSet() {
        // אתחול המערך הממוין
        this.sortedArray = new MySortedArray<>();
    }
	
	//חיפוש ב O(log n)
	// נשתמש בחיפוש הבינארי של המערך הממוין 
    public Element<T> search(int k) {
        return this.sortedArray.search(k);
    }
	
	//הסופה ב O(n)
	//  נמיר את האלמנט ל-ArrayElement ומוסיפים למערך
    public void insert(Element<T> x) {
        // אנחנו עוטפים את האלמנט באובייקט מסוג ArrayElement כדי שיהיה לו אינדקס
        if (x instanceof ArrayElement) {
             this.sortedArray.insert((ArrayElement<T>) x);
        } else {
             // אם זה סתם אלמנט, יוצרים ממנו ArrayElement חדש
             this.sortedArray.insert(new ArrayElement<>(x));
        }
    }
	
	// מחיקה ב O(n)
	//  ממירים ומבקשים מהמערך למחוק 
    public void delete(Element<T> x) {
        // מניחים שהאלמנט כבר נמצא במבנה ולכן הוא בטוח מסוג ArrayElement
        this.sortedArray.delete((ArrayElement<T>) x);
    }
	
	// מינימום ב O(1)
	// האיבר הראשון במערך הממוין
    public Element<T> minimum() {
        if (this.sortedArray.size() == 0) {
            return null;
        }
        return this.sortedArray.get(0);
    }
	
	//מקסימום ב O(1)
	//האיבר האחרון במערך הממוין 
    public Element<T> maximum() {
        if (this.sortedArray.size() == 0) {
            return null;
        }
        return this.sortedArray.get(this.sortedArray.size() - 1);
    }
	
	//סקססור ב O(1)
	// האיבר שנמצא באינדקס הבא
    public Element<T> successor(Element<T> x) {
        // המרה כדי לקבל גישה לשדה האינדקס
        ArrayElement<T> arrElement = (ArrayElement<T>) x;
        int nextIndex = arrElement.index() + 1;

        // בדיקה שלא חרגנו מגבולות המערך
        if (nextIndex >= this.sortedArray.size()) {
            return null;
        }
        return this.sortedArray.get(nextIndex);
    }
	
	//פרידססור ב O(1)
	// האיבר שנמצא באינדקס הקודם
    public Element<T> predecessor(Element<T> x) {
        // המרה כדי לקבל גישה לשדה האינדקס
        ArrayElement<T> arrElement = (ArrayElement<T>) x;
        int prevIndex = arrElement.index() - 1;

        // בדיקה שלא חרגנו מגבולות המערך
        if (prevIndex < 0) {
            return null;
        }
        return this.sortedArray.get(prevIndex);
    }
}

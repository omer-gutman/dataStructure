
public class MyDataStructure {
    // עץ עבור מוצרים עם איכות 1-5
    private MyAVLTree<Product> treeRest;

    // עץ עבור מוצרים עם איכות 0
	// המידע הכל צומת בעץ הוא הלינק ברשימה המקושרת ככה נוכל מחיקה בזמן מיידי
    private MyAVLTree<Link<Product>> treeZero;

    // רשימה מקושרת שמחזיקה רק את המוצרים עם איכות 0 עבור JunkWorst
    private MyLinkedList<Product> listZero;

    // מערך ממוין באינדקס i נשמור כמה מוצרים יש עם איכות i
    private int[] qualityCounts;
    
    // סכום האיכויות הכולל לחישוב ממוצע
    private long sumQuality;
    
    // סך כל האיברים במבנה
    private int totalSize;

    /***
     * This function is the Init function.
     */
    public MyDataStructure() {
        this.treeRest = new MyAVLTree<>();
        this.treeZero = new MyAVLTree<>();
        this.listZero = new MyLinkedList<>();
        this.qualityCounts = new int[6]; // איכויות 0 עד 5
        this.sumQuality = 0;
        this.totalSize = 0;
    }
	
	public void insert(int id, int quality) {
        Product p = new Product(id, quality);

        if (quality == 0) {
            // יצירת חוליה לרשימה
            Link<Product> link = new Link<>(id, p);
            // הוספה לרשימה
            this.listZero.insert(link);
            
            // יצירת צומת לעץ שמחזיק הצבעה לחוליה שיצרנו הרגע
            TreeNode<Link<Product>> node = new TreeNode<>(id, link);
            // הוספה לעץ ה-0
            this.treeZero.insert(node);
        } else {
            // הוספה רגילה לעץ של שאר האיכויות
            TreeNode<Product> node = new TreeNode<>(id, p);
            this.treeRest.insert(node);
        }

        // עדכון סטטיסטיקות
        this.qualityCounts[quality]++;
        this.sumQuality += quality;
        this.totalSize++;
    }
	
	public void delete(int id) {
        // נסה לחפש בעץ של האיכויות הגבוהות
        TreeNode<Product> nodeRest = this.treeRest.search(id);
        
        if (nodeRest != null) {
            // מצאנו! נמחק מהעץ ונעדכן סטטיסטיקה
            int q = nodeRest.satelliteData().quality();
            this.treeRest.delete(nodeRest);
            
            this.qualityCounts[q]--;
            this.sumQuality -= q;
            this.totalSize--;
        } else {
            // לא מצאנו, נסה לחפש בעץ ה0
            TreeNode<Link<Product>> nodeZero = this.treeZero.search(id);
            
            if (nodeZero != null) {
                // מצאנו בעץ ה0 
                // השדה satelliteData מחזיק את הלינק המתאים ברשימה
                Link<Product> linkToDelete = nodeZero.satelliteData();
                
                // מחיקה מהרשימה ב O(1) כי יש לנו אתה לינק
                this.listZero.delete(linkToDelete);
                // מחיקה מהעץ
                this.treeZero.delete(nodeZero);
                
                // עדכון סטטיסטיקה
                this.qualityCounts[0]--;
                // אין צורך להפחית מ-sumQuality כי האיכות היא 0
                this.totalSize--;
            }
        }
    }
	
	public int medianQuality() {
        if (this.totalSize == 0) return -1;
        
        // החציון נמצא במקום ה-(totalSize / 2)
        int medianIndex = this.totalSize / 2; 
        
        int count = 0;
        for (int i = 0; i < 6; i++) {
            count += this.qualityCounts[i];
            if (count > medianIndex) { // עברנו את החצי
                return i;
            }
        }
        return -1; // לא אמור לקרות
    }
    
    public double avgQuality() {
        if (this.totalSize == 0) return -1;
        return (double) this.sumQuality / this.totalSize;
    }

	
	public MyLinkedList<Product> junkWorst() {
        // שומרים את הרשימה הנוכחית בצד כדי להחזיר אותה
        MyLinkedList<Product> deletedItems = this.listZero;
        
        // מאפסים את המבנים של איכות 0 ב O(1)
        this.listZero = new MyLinkedList<>();
        this.treeZero = new MyAVLTree<>();
        
        // מעדכנים את הגודל הכולל
        this.totalSize -= this.qualityCounts[0];
        
        // מאפסים את המונה של איכות 0
        this.qualityCounts[0] = 0;
        
        // הסכום sumQuality לא משתנה כי מחקנו רק אפסים
        
        return deletedItems;
    }
	
}

/**
 * This class represents a product,
 * with id and quality.
 */
public class Product {
	private int id;
	private int quality;
	
	public Product(int id, int quality) {
		this.id = id;
		this.quality = quality;
	}
	
	public int id() {
		return this.id;
	}
	
	public int quality() {
		return this.quality;
	}
	
	public boolean equals(Object other){
		boolean ans = false;        
		if (other instanceof Product) {
			Product castedOther = (Product) other;  
            ans = this.id() == castedOther.id() & this.quality() == castedOther.quality();
        }        
        return ans;
	}
	
}

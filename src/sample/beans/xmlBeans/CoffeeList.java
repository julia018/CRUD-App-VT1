package sample.beans.drinkLists;

import sample.beans.Coffee;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "coffeeList")
@XmlAccessorType(XmlAccessType.FIELD)
public class CoffeeList {

    @XmlElement(name = "coffee")
    private List<Coffee> coffeeList = null;

    public List<Coffee> getCoffeeList() {
        return coffeeList;
    }

    public void setCoffeeList(List<Coffee> coffeeList) {
        this.coffeeList = coffeeList;
    }
}

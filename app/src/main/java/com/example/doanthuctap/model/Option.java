package com.example.doanthuctap.model;


import com.example.doanthuctap.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Phong-Kaster
 * this class creates options for customized spinner
 */
public class Option {
    private String name;
    private int icon;

    public Option(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public Option() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * @author Phong-Kaster
     * @return list of manufacturers option for spinner
     */
    public static List<Option> getManufacturerOption()
    {
        List<Option> options = new ArrayList<>();

        /*manufacturer NOTHING*/
        Option manufacturer0 = new Option();
        manufacturer0.setIcon(R.drawable.ic_nothing);
        manufacturer0.setName("");

        /*manufacturer ACER*/
        Option manufacturer1 = new Option();
        manufacturer1.setIcon(R.drawable.logo_acer);
        manufacturer1.setName("ACER");

        /*manufacturer ASUS*/
        Option manufacturer2 = new Option();
        manufacturer2.setIcon(R.drawable.logo_asus);
        manufacturer2.setName("ASUS");


        /*manufacturer DELL*/
        Option manufacturer3 = new Option();
        manufacturer3.setIcon(R.drawable.logo_dell);
        manufacturer3.setName("DELL");

        /*manufacturer HP*/
        Option manufacturer4 = new Option();
        manufacturer4.setIcon(R.drawable.logo_hp);
        manufacturer4.setName("HP");

        /*manufacturer MSi*/
        Option manufacturer5 = new Option();
        manufacturer5.setIcon(R.drawable.logo_msi);
        manufacturer5.setName("MSi");

        options.add(manufacturer0);
        options.add(manufacturer1);
        options.add(manufacturer2);
        options.add(manufacturer3);
        options.add(manufacturer4);
        options.add(manufacturer5);

        return options;
    }

    /**
     * @author Phong-Kaster
     * @return list of manufacturers option for spinner
     */
    public static List<Option> getGraphicCardOptions(){
        List<Option> options = new ArrayList<>();

        /*graphic card NOTHING*/
        Option card0 = new Option();
        card0.setIcon(R.drawable.ic_nothing);
        card0.setName("");

        /*graphic card NVIDIA*/
        Option card1 = new Option();
        card1.setIcon(R.drawable.logo_nvidia);
        card1.setName("NVIDIA");

        /*graphic card AMD*/
        Option card2 = new Option();
        card2.setIcon(R.drawable.logo_amd);
        card2.setName("AMD");

        options.add(card0);
        options.add(card1);
        options.add(card2);

        return options;
    }

    /**
     * @author Phong-Kaster
     * @return
     */
    public static List<Option> getDemandOptions(){
        List<Option> options = new ArrayList<>();

        /*demand NOTHING*/
        Option demand0 = new Option();
        demand0.setIcon(R.drawable.ic_nothing);
        demand0.setName("");


        /*demand Gaming*/
        Option demand1 = new Option();
        demand1.setIcon(R.drawable.logo_gaming);
        demand1.setName("Gaming");


        /*demand Student*/
        Option demand2 = new Option();
        demand2.setIcon(R.drawable.logo_student);
        demand2.setName("Student");

        /*demand Office*/
        Option demand3 = new Option();
        demand3.setIcon(R.drawable.logo_office);
        demand3.setName("Office");

        /*demand Gaming*/
        Option demand4 = new Option();
        demand4.setIcon(R.drawable.logo_design);
        demand4.setName("Graphic");

        /*demand Gaming*/
        Option demand5 = new Option();
        demand5.setIcon(R.drawable.logo_lightweight);
        demand5.setName("Lightweight");

        options.add(demand0);
        options.add(demand1);
        options.add(demand2);
        options.add(demand3);
        options.add(demand4);
        options.add(demand5);
        return options;
    }
}

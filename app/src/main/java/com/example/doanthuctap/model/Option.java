package com.example.doanthuctap.model;


import android.content.Context;

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

        /*manufacturer MSi*/
        Option manufacturer6 = new Option();
        manufacturer6.setIcon(R.drawable.logo_lenovo);
        manufacturer6.setName("Lenovo");

        options.add(manufacturer0);
        options.add(manufacturer1);
        options.add(manufacturer2);
        options.add(manufacturer3);
        options.add(manufacturer4);
        options.add(manufacturer5);
        options.add(manufacturer6);

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
        demand4.setName("Design");

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

    /**
     * @author Phong-Kaster
     * @return order status
     */
    public static List<Option> getOrderStatus(Context context)
    {
        List<Option> options = new ArrayList<>();

        String processing = context.getString(R.string.to_pay);
        String verified = context.getString(R.string.verified);
        String packed = context.getString(R.string.packed);
        String beingTransported = context.getString(R.string.being_transported);
        String done = context.getString(R.string.done);
        String cancel = context.getString(R.string.cancel);

        /*order status processing*/
        Option status0 = new Option();
        status0.setIcon(R.drawable.ic_to_pay_2);
        status0.setName(processing);


        /*demand Gaming*/
        Option status1 = new Option();
        status1.setIcon(R.drawable.ic_verified);
        status1.setName(verified);


        /*demand Student*/
        Option status2 = new Option();
        status2.setIcon(R.drawable.ic_packed_2);
        status2.setName(packed);

        /*demand Office*/
        Option status3 = new Option();
        status3.setIcon(R.drawable.ic_being_transported_2);
        status3.setName(beingTransported);

        /*demand Gaming*/
        Option status4 = new Option();
        status4.setIcon(R.drawable.ic_done_2);
        status4.setName(done);

        /*demand Gaming*/
        Option status5 = new Option();
        status5.setIcon(R.drawable.ic_cancel_2);
        status5.setName(cancel);

        options.add(status0);
        options.add(status1);
        options.add(status2);
        options.add(status3);
        options.add(status4);
        options.add(status5);

        return options;
    }

    /**
     * @author Phong-Kaster
     * @return list of screen size
     */
    public static List<Option> getScreenSize()
    {
        List<Option> options = new ArrayList<>();

        String screen13inch = "13";
        String screen14inch = "14";
        String screen15inch = "15.6";
        String screen17inch = "17.3";

        /*order status processing*/
        Option option0 = new Option();
        option0.setIcon(R.drawable.ic_13_inch);
        option0.setName(screen13inch);


        /*demand Gaming*/
        Option option1 = new Option();
        option1.setIcon(R.drawable.ic_14_inch);
        option1.setName(screen14inch);


        /*demand Student*/
        Option option2 = new Option();
        option2.setIcon(R.drawable.ic_15_inch);
        option2.setName(screen15inch);

        /*demand Office*/
        Option option3 = new Option();
        option3.setIcon(R.drawable.ic_17_inch);
        option3.setName(screen17inch);

        options.add(option0);
        options.add(option1);
        options.add(option2);
        options.add(option3);

        return options;
    }
}

package Economy;

import DataStructs.Tuple;
import PeopleStuff.Goblin;
import PeopleStuff.Personality;

/*
 * Brant Eckert, 12/19/20
 * Goblin job, has a description, a required trait, an amt for that trait and a paycheck amt.
 * The goblin economy is VERY UNFINISHED.
 */
public class Job {
    private String desc = "";
    private String name = "";
    private String reqTrait = "Cunning";
    private int reqAmt = 0;
    private Goblin worker = null;
    private Business business = null;
    private int paycheck = 0;

    /**
     * Instantiates a new Job object
     * @param name Name of the job
     * @param desc Description of the job
     * @param reqTrait Required trait for the job
     * @param reqAmt Required amount of that trait for the job
     * @param pay Pay for this job.
     */
    public Job(String name, String desc,  String reqTrait, int reqAmt, int pay){
        this.name = name;
        this.desc = desc;
        if(Personality.TRAITS.contains(reqTrait)){
            this.reqTrait = reqTrait;
            this.reqAmt = reqAmt;
        }
        paycheck = pay;
    }

    /**
     * Instantiates a new Job object with a parent Business.
     * @param name Name of the job
     * @param desc Description of the job
     * @param reqTrait Required trait for the job
     * @param reqAmt Required amount of that trait for the job
     * @param pay Pay for this job.
     * @param member Business this job exists in.
     */
    public Job(String name, String desc,  String reqTrait, int reqAmt, int pay, Business member){
        this(name, desc, reqTrait, reqAmt, pay);
        business = member;
    }

    /**
     * Set the business that owns this job.
     * @param bus Business that owns this job.
     */
    public void setBusiness(Business bus){
        business = bus;
    }
    public String getName(){
        return name;
    }
    public void setName(String nName){
        name = nName;
    }
    public String getDesc(){
        return desc;
    }
    public void setDesc(String nDesc){
        desc = nDesc;
    }

    public String getReqTrait(){
        return reqTrait;
    }
    public int getReqAmt(){
        return reqAmt;
    }
    public Tuple<String, Integer> getJobSpecs(){
        return new Tuple<>(reqTrait, reqAmt);
    }
    public int getPaycheck;

    /**
     * Change the specifications of the job.
     * @param newReq New required trait
     * @param newAmt New amount required.
     * @param grandfather True if old employee is grandfathered in; false if employee is not grandfathered in.
     */
    public void changeSpecifications(String newReq, int newAmt, boolean grandfather){
        reqTrait = newReq;
        reqAmt = newAmt;
        if(!this.qualify(worker) && !grandfather){
            worker.setEmployed(false);
            worker = null;
        }
    }

    /**
     * Change the specifications of the job without concern for grandfathering.
     * @param newReq New required trait
     * @param newAmt New required amount
     */
    public void changeSpecifications(String newReq, int newAmt){
        changeSpecifications(newReq, newAmt, false);
    }

    /**
     * Let goblin guy apply for a job.
     * @param guy The goblin applying for the job.
     * @return True if employed, false if not.
     */
    public boolean apply(Goblin guy){
        if(qualify(guy)){
            if(worker != null)
                worker.setEmployed(false);
            worker = guy;
            guy.setEmployed(true);
            guy.setJob(this);
            return true;
        }
        return false;
    }

    /**
     * Check to see if goblin guy qualifies for the job.
     * @param guy Goblin applying
     * @return True if qualifies, false if not.
     */
    public boolean qualify(Goblin guy){
        if(guy.getPersonality().getPersonalityValue(reqTrait) >= reqAmt){
            return true;
        }
        return false;
    }

    public Goblin getWorker(){
        return worker;
    }

    public boolean isOccupied(){
        if(worker == null)
            return true;
        return false;
    }

    /**
     * Fires the current employee working this job.
     */
    public void fire(){
        if(worker != null) {
            worker.setEmployed(false);
            worker.setJob(null);
        }
        worker = null;

    }

    /**
     * Pay all of the workers the business can and fire the ones who cannot be paid.
     */
    public void payroll(){
        if(business.getAccounts().checkBalance() <= paycheck){
            if(worker != null) {
                worker.setEmployed(false);
                worker = null;
            }
            business.remove(this);
        }
        else if(worker != null){
            worker.getWallet().deposit(paycheck);
        }
    }

    /**
     * Clone this job
     * @return Cloned job.
     */
    public Job clone(){
        return new Job(name, desc, reqTrait, reqAmt, paycheck, business);
    }

    /**
     * See if the job is equivalent. Only checks name and description
     * @param o Object to compare
     * @return True if equivalent, false if not.
     */
    public boolean equals(Object o){
        if(o instanceof Job){
            Job oj = (Job)o;
            if(oj.name.equals(name) &&
            oj.desc.equals(desc))
                return true;
        }
        return false;
    }


}

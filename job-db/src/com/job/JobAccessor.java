package com.job;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.types.UInteger;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static novemb.jooq.model.Tables.*;


public class JobAccessor {
    PrintStream output;
    InputStream inputs;
    DSLContext dsl;
    Scanner scan;

    public JobAccessor(PrintStream output, InputStream inputs, DSLContext dsl) {
        this.output = output;
        this.inputs = inputs;
        this.dsl = dsl;
        this.scan = new Scanner(this.inputs);
    }

    public void run() {
        while(true){
            this.output.println("press n for new job, u for update job, or q for quit");
            String option = scan.nextLine();
            if (option.equals("n")){
                this.newJob();
            } else if (option.equals("u")){
                this.modJob();
            } else if (option.equals("q")){
                this.output.println("bye-bye!");
                break;
            }else{
                continue; //superfluous code
            }
        }
    }

    public void newJob() {
        this.output.println("enter the job name");
        String name = scan.nextLine();

        this.output.println("enter the location name");
        String locName = scan.nextLine();

        this.output.println("enter the url");
        String url = scan.nextLine();

        try{
            Thread.sleep(500);
        } catch (InterruptedException e){
            System.out.println("Warning: the thread was not able to sleep.");
            e.printStackTrace();
        }

        this.output.println("thanks for the url");
        try{
            Thread.sleep(800);
        } catch (InterruptedException e){
            System.out.println("Warning: the thread was not able to sleep.");
            e.printStackTrace();
        }


        UInteger realStatus = this.getId(
                "here is a list of status ids:",
                dsl.selectFrom(STATUS_TYPE).fetch(),
                "enter the id that corresponds to your job's current status"
                );

        this.output.println("any notes");
        String notes = scan.nextLine(); //TODO StringUtils.isEmpty()

        Query q = dsl.insertInto(JOB, JOB.JOB_NAME, JOB.LOCATION_NAME, JOB.URL).values(name, locName, url);
//        this.output.println(q);
        q.execute();
        Object insert_id = dsl.fetch("select last_insert_id()").getValue(0,0);
        UInteger actual_id = UInteger.valueOf(insert_id.toString());
//        this.output.println(insert_id);
        Query q2 = dsl.insertInto(STATUS,STATUS.JOB_ID,STATUS.TYPE,STATUS.NOTES).values(actual_id,realStatus,notes);
//        this.output.println(q2);
        q2.execute();

//        this.output.println(dsl.fetch(String.format("select id, job_name, location_name, url, stat.type, stat.name, stat.as_of from job left join(select job_id, as_of, type, type.name from status left join (select status_type_id, name from status_type) as type on status.type = type.status_type_id) as stat on job.id = stat.job_id where id = %s",actual_id)));
        this.output.println(dsl.select(JOB.ID,JOB.JOB_NAME,JOB.LOCATION_NAME,JOB.URL,STATUS.TYPE,STATUS_TYPE.NAME,STATUS.AS_OF).from(JOB.leftJoin(STATUS.leftJoin(STATUS_TYPE).on(STATUS.TYPE.eq(STATUS_TYPE.STATUS_TYPE_ID))).on(JOB.ID.eq(STATUS.JOB_ID))).where(JOB.ID.eq(actual_id)).fetch());


    }

    public void modJob(){

        String likeString = "";

        while(true){
            this.output.println("give me a few letters from the job name");
            likeString = this.scan.nextLine();

            this.output.println("here are the jobs that match:");
            this.output.println(dsl.select(JOB.ID,JOB.JOB_NAME,JOB.LOCATION_NAME,STATUS.TYPE,STATUS.AS_OF,STATUS.NOTES,STATUS_TYPE.NAME).from(JOB.leftJoin(STATUS.leftJoin(STATUS_TYPE).on(STATUS_TYPE.STATUS_TYPE_ID.eq(STATUS.TYPE))).on(STATUS.JOB_ID.eq(JOB.ID))).where(JOB.JOB_NAME.like("%"+likeString+"%")).fetch());
//            this.output.println(dsl.fetch("select id, job_name, location_name, status.type, status.as_of, status.notes, status.name from job left join (select status_id, job_id, type, as_of, notes, stat.name from status left join (select status_type_id, name from status_type) as stat on stat.status_type_id = status.type where as_of in (select max(as_of) from status group by job_id)) as status on status.job_id = job.id where job_name like \"%" + likeString + "%\""));
            try{
                Thread.sleep(500);
            } catch (InterruptedException e){
                System.out.println("Warning: the thread was not able to sleep.");
                e.printStackTrace();
            }
            this.output.println("retry? y/n");
            if (!this.scan.nextLine().equals("y")){
                break;
            }
        }

        UInteger job = this.getId( "enter the id of the job whose status you'd like to change");

        UInteger status = this.getId(
                "here is a list of status ids:",
                dsl.selectFrom(STATUS_TYPE).fetch(),
                "enter the id that corresponds to your job's new status");

        this.output.println("any notes?");
        String notes = this.scan.nextLine();

        Query q = dsl.insertInto(STATUS, STATUS.JOB_ID, STATUS.TYPE, STATUS.NOTES).values(job, status, notes);
//        this.output.println(q);
        q.execute();

        this.output.println(dsl.fetch("select id, job_name, location_name, url, stat.type, stat.name, stat.as_of from job left join(select job_id, as_of, type, type.name from status left join (select status_type_id, name from status_type) as type on status.type = type.status_type_id) as stat on job.id = stat.job_id where id = "+job.toString()+" order by as_of desc limit 1"));

    }

    UInteger getId(Object ... messages){
        while(true){
            try{
                for(Object i : messages){
                    this.output.println(i);
                }
                String id = this.scan.nextLine();
                return UInteger.valueOf(id);

            }catch(NumberFormatException e){
                this.output.println("you must enter single-byte digits for the id");
                continue;
            }
        }
    }


}

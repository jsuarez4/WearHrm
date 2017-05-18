package com.zero.nimo.wearhrm;

/**
 * Created by jer on 4/26/17.
 */

public class HeartLogs {
        private int id;
        private String date;
        private String average;
        private String time;
        public HeartLogs()
        {
        }
        public HeartLogs(int id,String date,String average,String time)
        {
            this.id=id;
            this.date=date;
            this.average=average;
            this.time=time;
        }
        public void setId(int id) {
            this.id = id;
        }
        public void setDate(String date) {
            this.date = date;
        }
        public void setTime(String time){
            this.time=time;
        }

        public void setAverage(String average) {
            this.average = average;
        }
        public int getId() {
            return id;
        }
        public String getAverage() {
            return average;
        }
        public String getDate() {
            return date;
        }
        public String getTime(){
            return time;
        }
    }
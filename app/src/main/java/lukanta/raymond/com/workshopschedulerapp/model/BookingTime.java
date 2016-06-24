package lukanta.raymond.com.workshopschedulerapp.model;

import java.util.Date;

/**
 * Created by raymondlukanta on 24/06/16.
 */
public class BookingTime {
    private Date startDate;
    private Date endDate;
    private boolean fullyBooked;

    public BookingTime(Date startDate, Date endDate, boolean fullyBooked) {
        this.endDate = endDate;
        this.fullyBooked = fullyBooked;
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isFullyBooked() {
        return fullyBooked;
    }

    public void setFullyBooked(boolean fullyBooked) {
        this.fullyBooked = fullyBooked;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}

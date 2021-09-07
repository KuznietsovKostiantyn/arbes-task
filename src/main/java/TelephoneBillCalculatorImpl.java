import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TelephoneBillCalculatorImpl implements TelephoneBillCalculator{

    @Override
    public BigDecimal calculate(String phoneLog) {

        String[] callInfo = phoneLog.split(",");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(callInfo[1], formatter);
        LocalDateTime endTime = LocalDateTime.parse(callInfo[2], formatter);
        //TODO for telephone numbers use HashMap<String, Integer>
        return calculateMoney(startTime, endTime);
    }
    public BigDecimal calculateMoney (LocalDateTime startTime, LocalDateTime endTime){
        Duration diff = Duration.between(startTime, endTime);
        if (diff.toDays() < 1){
            return payForOneDay(startTime, endTime);
        }
        else{
            //TODO
            return new BigDecimal(0);
        }
    }

    public BigDecimal payForOneDay (LocalDateTime startTime, LocalDateTime endTime){
        LocalDateTime eightAm = LocalDate.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth()).atTime(8, 0,0);
        LocalDateTime sixPm = LocalDate.of(endTime.getYear(), endTime.getMonth(), endTime.getDayOfMonth()).atTime(18, 0,0);
        BigDecimal total = new BigDecimal(1);
        Duration diffTime = Duration.between(startTime, endTime);
        boolean isLongerThanFiveMin = false;
        if (diffTime.toMinutes() >= 5){
            isLongerThanFiveMin = true;
            total = new BigDecimal("0.2");
        }

        if (startTime.isBefore(eightAm)){
            Duration diff = Duration.between(startTime, eightAm);
            if(isLongerThanFiveMin){
                total = total.add(BigDecimal.valueOf(diff.toMinutes() * 0.2));
            }
            else{
                total = total.add(new BigDecimal(diff.toMinutes()).multiply(new BigDecimal("0.5")));
            }

            if (endTime.isAfter(sixPm)){
                Duration diff1 = Duration.between(endTime, sixPm);
                if(isLongerThanFiveMin){
                    total = total.add(new BigDecimal(diff1.toMinutes()).multiply(new BigDecimal("0.3")));
                }
                else{
                    total = total.add(new BigDecimal(diff1.toMinutes()).multiply(new BigDecimal("0.5")));
                }
            }
            else{
                Duration diff2 = Duration.between(eightAm, endTime);
                if(isLongerThanFiveMin){
                    total = total.add(BigDecimal.valueOf(diff2.toMinutes() * 0.2));
                }
                else{
                    total = total.add(new BigDecimal(diff2.toMinutes()));
                }
            }

            if (endTime.isBefore(eightAm)){
                Duration diff3 = Duration.between(startTime, endTime);
                if(isLongerThanFiveMin){
                    total = total.add(BigDecimal.valueOf(diff3.toMinutes() * 0.2));
                }
                else{
                    total = total.add(new BigDecimal(diff3.toMinutes()));
                }
            }
        }
        else if (startTime.isAfter(eightAm) && startTime.isAfter(sixPm)) {
            if (endTime.isAfter(sixPm) && endTime.isBefore(eightAm.plusDays(1)) ){
                Duration diff4 = Duration.between(startTime, endTime);
                if(isLongerThanFiveMin){
                    total = total.add(new BigDecimal(diff4.toMinutes()).multiply(new BigDecimal("0.3")));
                }
                else{
                    total = total.add(new BigDecimal(diff4.toMinutes()).multiply(new BigDecimal("0.5")));
                }
            }
        }
        else if (startTime.isAfter(eightAm) && startTime.isBefore(sixPm)){
            if (endTime.isBefore(sixPm)){
                Duration diff5 = Duration.between(startTime, endTime);
                if(isLongerThanFiveMin){
                    total = total.add(BigDecimal.valueOf(diff5.toMinutes() * 0.2));
                }
                else{
                    total = total.add(new BigDecimal(diff5.toMinutes()));
                }
            }
        }
        System.out.println(total);
        return total;
    }
}

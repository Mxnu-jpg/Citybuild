package at.htblakaindorf.AHIF18.Ground.Behaviours;

public class ProduceBad implements ProduceBehaviour{
    @Override
    public int[] produce(int[] earnings) {

        for (int i = 0; i < earnings.length; i++) {
            earnings[i] = (int) (earnings[i] * 0.8);
        }
        return earnings;
    }
}

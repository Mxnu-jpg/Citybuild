package at.htblakaindorf.AHIF18.Ground.Behaviours;

public class ProduceFishGood implements ProduceBehaviour{
    @Override
    public int[] produce(int[] earnings) {
        if(!(earnings[0] <= 0))
            earnings[0] = (int) (earnings[0] * 1.4);
        return earnings;
    }
}
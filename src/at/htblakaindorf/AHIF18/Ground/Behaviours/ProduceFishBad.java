package at.htblakaindorf.AHIF18.Ground.Behaviours;

public class ProduceFishBad implements ProduceBehaviour{
    /**
     * Sets the earnings of each building based on the produce-behaviour
     *
     * @return */
    @Override
    public int[] produce(int[] earnings) {
        if(!(earnings[0] <= 0))
            earnings[0] = (int)(earnings[0] * 0.8);
        return earnings;
    }
}

package at.htblakaindorf.AHIF18.Ground.Behaviours;

public class ProduceIronBad implements ProduceBehaviour {
    @Override
    public int[] produce(int[] earnings) {
            earnings[3] = (int) (earnings[3] * 0.7);
        return earnings;
    }
}

package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 *  Bad Produce-Behaviour for {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 *  placed on a {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Sand} tile
 *
 * @author Marcel Schmidl
 * @version 1.0
 * */
public class ProduceBad implements ProduceBehaviour{
    /**
     * Sets the earnings of each building based on the produce-behaviour
     *
     * @return */
    @Override
    public int[] produce(int[] earnings) {

        for (int i = 0; i < earnings.length; i++) {
            earnings[i] = (int) (earnings[i] * 0.8);
        }
        return earnings;
    }
}

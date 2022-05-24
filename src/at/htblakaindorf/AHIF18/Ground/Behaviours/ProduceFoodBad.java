package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 *  Bad Produce-Behaviour for {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 *  placed on a {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Sand} tile
 *
 * @author Marcel Schmidl
 * @version 1.0
 * */
public class ProduceFoodBad implements ProduceBehaviour{
    /**
     * Sets the earnings of each building based on the produce-behaviour
     *
     * @return */
    @Override
    public int[] produce(int[] earnings) {
            earnings[0] = (int)(earnings[0]*0.8);
        return earnings;
    }
}

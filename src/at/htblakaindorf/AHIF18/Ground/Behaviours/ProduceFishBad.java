package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 * Bad Fish Produce-Behaviour for {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 * placed on a {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Water} tile
 *
 * @author Manuel Reinprecht
 * @version 1.0 - 16.05.2022
 */
public class ProduceFishBad implements ProduceBehaviour {
    /**
     * Sets the earnings of each building based on the produce-behaviour
     *
     * @return earnings based on the ground tile
     */
    @Override
    public int[] produce(int[] earnings) {
        if (!(earnings[0] <= 0))
            earnings[0] = (int) (earnings[0] * 0.8);
        return earnings;
    }
}

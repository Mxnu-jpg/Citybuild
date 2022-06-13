package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 * Average Food Produce-Behaviour for {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 * placed on a {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Grass} tile
 *
 * @author Marcel Schmidl
 * @version 1.0 - 16.05.2022
 */
public class ProduceFoodAverage implements ProduceBehaviour {
    /**
     * Sets the earnings of each building based on the produce-behaviour
     *
     * @return earnings based on the ground tile
     */
    @Override
    public int[] produce(int[] earnings) {
        return earnings;
    }
}

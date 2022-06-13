package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 * <b>CityBuild</b><br>
 * Bad Iron Produce-Behaviour for {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 * placed on a {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Ironfield} tile
 *
 * @author Manuel Reinprecht
 * @version 1.0
 * @since 16.05.2022
 */
public class ProduceIronBad implements ProduceBehaviour {
    /**
     * Sets the earnings of each building based on the produce-behaviour
     *
     * @return earnings based on the ground tile
     */
    @Override
    public int[] produce(int[] earnings) {
        earnings[3] = (int) (earnings[3] * 0.8);
        return earnings;
    }
}

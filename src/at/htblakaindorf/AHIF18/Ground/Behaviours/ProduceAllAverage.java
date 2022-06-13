package at.htblakaindorf.AHIF18.Ground.Behaviours;

/**
 *  Average Produce-Behaviour for all {@link at.htblakaindorf.AHIF18.Ground.Buildingobjects.Building}
 *
 * @author Marcel Schmidl
 * @version 1.0 - 16.05.2022
 * */
public class ProduceAllAverage implements ProduceBehaviour{
    /**
     * Sets the earnings of each building based on the produce-behaviour
     *
     * @return earnings based on the ground tile*/
    @Override
    public int[] produce(int[] earnings) {
        return earnings;
    }
}

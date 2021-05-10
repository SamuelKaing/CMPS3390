package skaing.finalproject;

public class Player implements GameObject{
    private float health = 100f, speed = 1;
    private int level = 1, skillPoints = 0;
    private float xp = 0, xpReq = 25;

    public void Player() {}


    @Override
    public void update() {

    }

    /**
     * Getter for player speed
     * @return float player speed
     */
    @Override
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Setter for player speed
     * @param speed float which determines battle order
     */
    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }       // Make sure to reset speed after fight

    /**
     * Getter for health
     * @return float which is player health
     */
    @Override
    public float getHealth() {
        return this.health;
    }

    /**
     * Subtracts player health when called
     * @param damageTaken float amount of damage taken
     */
    @Override
    public void takeDamage(float damageTaken) {
        this.health -= damageTaken;
    }

    /**
     * Adds to player health when called
     * @param amountHealed float amount of health healed
     */
    @Override
    public void addHealth(float amountHealed) {
        this.health += amountHealed;
    }

    /**
     * Levels up if requirement is met
     * @param xpGained float xp gained from event
     */
    @Override
    public void addXP(float xpGained) {
        this.xp += xpGained;
        float tmp;
        
        // Levels up while xpReq is met
        while(this.xp >= xpReq) {
            // Get difference
            // Will not be negative (xp is greater than req)
            tmp = this.xp - xpReq;
            
            // XP is set to whats left after xpReq is subtracted
            this.xp = tmp;

            // Increase level
            level++;

            // Give skill point
            skillPoints++;

            // Make level ups harder
            xpReq += 25 * (level * 1.5);
        }
    }
}

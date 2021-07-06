package com.codemul.miwok;

/**
 * {@link Word} represents a vocabulary word that the user wants to learn.
 * It contains a default translation and a Miwok translation for that word.
 */
public class Word {
    /* Default translation for the word */
    private String defaultTranslation;

    /* Miwok translation for the word */
    private String miwokTranslation;

    /** Image resource ID for the word */
    private int imageResourceID = NO_IMAGE_PROVIDED;

    //tidak harus huruf besar
    // Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    //Audio
    private int audioResourceId;

    /*
     * Create a new Word object.
     *
     * @param defaultTranslation is the word in a language that the user is already familiar with
     *                           (such as English)
     * @param miwokTranslation is the word in the Miwok language
     */

    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId){
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.audioResourceId = audioResourceId;
    }
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceID, int audioResourceId){
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageResourceID = imageResourceID;
        this.setAudioResourceId(audioResourceId);
    }

    public Word(String lutti, int number_one, int number_one1) {
    }

    /**
     * Get the default translation of the word.
     */
    public String getDefaultTranslation(){
        return defaultTranslation;
    }

    /**
     * Get the Miwok translation of the word.
     */
    public String getMiwokTranslation(){
        return  miwokTranslation;
    }

    public int getImageResourceID(){
        return imageResourceID;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage(){
        return this.imageResourceID != NO_IMAGE_PROVIDED;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public void setAudioResourceId(int audioResourceId) {
        this.audioResourceId = audioResourceId;
    }
}

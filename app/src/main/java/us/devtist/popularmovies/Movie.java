package us.devtist.popularmovies;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import static java.io.FileDescriptor.out;

/**
 * Created by chris on 3/23/2017.
 */

public class Movie implements Parcelable {

    private String mTitle;
    private String mPosterPath;
    private Integer mRating;
    private Integer mId;
    private Integer mRuntime;
    private String mOverview;
    private String mReleaseYear;

    protected Movie(Parcel in) {
        mTitle = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mReleaseYear = in.readString();
        mId = in.readInt();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(int movieID) {
        mId = movieID;
    }

    public void setTitle(String title){
        mTitle = title;
    }

    public void setPosterPath(String poster){
        mPosterPath = poster;
    }

    public void setRating(Integer rating) {
        mRating = rating;
    }

    public void setId(Integer id){
        mId = id;
    }

    public void setRuntime(Integer runtime){
        mRuntime = runtime;
    }

    public void setOverview(String overview){
        mOverview = overview;
    }

    public void setReleaseYear(String year){ mReleaseYear = year;}

    public String getTitle(){
        return mTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public Integer getRating(){
        return mRating;
    }

    public Integer getId(){
        return mId;
    }

    public Integer getRuntime(){
        return mRuntime;
    }

    public String getOverview(){
        return mOverview;
    }

    public String getReleaseYear() {return mReleaseYear;}

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
       // dest.writeParcelable(mInfo, flags);
    }
}

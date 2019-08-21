package perusudroid.baseproject.data.model.api.response.stack;
/**
 * Awesome Pojo Generator
 * */
public class Items{
  private Owner owner;
  private Integer score;
  private Boolean is_accepted;
  private Integer last_activity_date;
  private Integer creation_date;
  private Integer answer_id;
  private Integer question_id;
  private Integer last_edit_date;
  public void setOwner(Owner owner){
   this.owner=owner;
  }
  public Owner getOwner(){
   return owner;
  }
  public void setScore(Integer score){
   this.score=score;
  }
  public Integer getScore(){
   return score;
  }
  public void setIs_accepted(Boolean is_accepted){
   this.is_accepted=is_accepted;
  }
  public Boolean getIs_accepted(){
   return is_accepted;
  }
  public void setLast_activity_date(Integer last_activity_date){
   this.last_activity_date=last_activity_date;
  }
  public Integer getLast_activity_date(){
   return last_activity_date;
  }
  public void setCreation_date(Integer creation_date){
   this.creation_date=creation_date;
  }
  public Integer getCreation_date(){
   return creation_date;
  }
  public void setAnswer_id(Integer answer_id){
   this.answer_id=answer_id;
  }
  public Integer getAnswer_id(){
   return answer_id;
  }
  public void setQuestion_id(Integer question_id){
   this.question_id=question_id;
  }
  public Integer getQuestion_id(){
   return question_id;
  }
  public void setLast_edit_date(Integer last_edit_date){
   this.last_edit_date=last_edit_date;
  }
  public Integer getLast_edit_date(){
   return last_edit_date;
  }
}
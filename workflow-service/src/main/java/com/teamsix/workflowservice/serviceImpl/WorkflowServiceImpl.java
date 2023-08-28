package com.teamsix.workflowservice.serviceImpl;

import com.teamsix.workflowservice.customException.GlobalException;
import com.teamsix.workflowservice.customException.WorkFlowAlreadyExist;
import com.teamsix.workflowservice.entity.Article;
import com.teamsix.workflowservice.entity.EditorChanges;
import com.teamsix.workflowservice.entity.ReviewerFeedback;
import com.teamsix.workflowservice.entity.WorkFlow;
import com.teamsix.workflowservice.openFeign.ArticleFeign;
import com.teamsix.workflowservice.payload.ArticleDto;
import com.teamsix.workflowservice.repo.ArticleRepo;
import com.teamsix.workflowservice.repo.EditorChangesRepo;
import com.teamsix.workflowservice.repo.ReviewerFeedbackRepo;
import com.teamsix.workflowservice.repo.WorkflowRepo;
import com.teamsix.workflowservice.service.WorkflowService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class WorkflowServiceImpl implements WorkflowService {
    @Autowired
    WorkflowRepo workflowRepo;
    @Autowired
    ReviewerFeedbackRepo reviewerFeedbackRepo;

    @Autowired
    ArticleRepo articleRepo;

    @Autowired
    EditorChangesRepo editorChangesRepo;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ArticleFeign articleFeign;


    @Override
    public WorkFlow saveWorkFlow(WorkFlow workFlow) {


        WorkFlow workFlowRes=this.getWorkFlowByArticleId(workFlow.getArticleId());
        if(workFlowRes!=null) throw new WorkFlowAlreadyExist("workflow status already exist for provided article id");
        ReviewerFeedback reviewerFeedback=workFlow.getReviewerFeedback();
        if(workFlow.getReviewerFeedback()!=null || workFlow.getReviewerFeedback().getId()=="")
            reviewerFeedbackRepo.save(reviewerFeedback);
        return workflowRepo.save(workFlow);
    }

    @Override
    public WorkFlow getWorkFlowById(String workFlowId) {
        return workflowRepo.findById(workFlowId).get();
    }

    @Override
    public List<WorkFlow> getAllWorkFlow() {
        return workflowRepo.findAll();
    }

    public WorkFlow getWorkFlowByArticleId(String articleId){
        return workflowRepo.findByArticleId(articleId);
    }

    @Override
    public WorkFlow changeWorkflowReviewStatus(String status, String articleId) {
        WorkFlow workFlowres=this.getWorkFlowByArticleId(articleId);
        if(status=="" || status==null) throw new GlobalException("review status empty or null");
        workFlowres.getReviewerFeedback().setReviewStatus(status);
       return workflowRepo.save(workFlowres);
    }

    @Override
    public WorkFlow addCommentsOnEditorChanges(String comment, String articleId, String editorChangeId) {
        WorkFlow workFlowres=this.getWorkFlowByArticleId(articleId);
        System.out.println(workFlowres);
        if(comment=="" || comment==null) throw new GlobalException("comment is empty or null");
        List<EditorChanges> l=workFlowres.getEditorChangesList();

        for(EditorChanges ec:l){

            if(ec.getId().equals(editorChangeId)){
                System.out.println("inside if");
                ec.setAuthorComment(comment);

                break;
            }


        }
        System.out.println(workFlowres);

        return workflowRepo.save(workFlowres);

    }

    @Override
    public WorkFlow UpdateWorkFlow(WorkFlow workFlow) {
        return null;
    }
  @CircuitBreaker(name = "assignEditorToArticle",fallbackMethod = "handleAssignEditorToArticle")
    @Override
    public WorkFlow assignEditorToArticle(String articleId, String editorId) {
        //ARTICLE SERVICE CALL TO GET ARTICLE OBJECT
//       List<Article> articleres= articleFeign.getArticleById(articleId).getBody();
//        if(Objects.nonNull(articleres)){
//            System.out.println("from article service");
//            System.out.println(articleres);
//        }
        Article article=articleRepo.findById(articleId).get();
        ArticleDto articleDto= modelMapper.map(article, ArticleDto.class);
        ReviewerFeedback reviewerFeedback= new ReviewerFeedback();
        reviewerFeedback.setReviewStatus("pending");
        List<EditorChanges> editorChangesList= new ArrayList<>();
        WorkFlow workFlow= new WorkFlow("",articleId,articleDto,editorId,"","","","",reviewerFeedback,editorChangesList);
        return this.saveWorkFlow(workFlow);
    }

    private String handleAssignEditorToArticle() {
        System.out.println("list of your article");
return "list of your article";
    }

    @Override
    public WorkFlow assignArticleToReviewer(String articleId, String reviewerId) {

        WorkFlow workFlow= this.getWorkFlowByArticleId(articleId);
        if(workFlow==null) throw new NoSuchElementException("article doesn't exist or editor not yet assigned or something went wrong");
        workFlow.setReviewerUserId(reviewerId);
        return workflowRepo.save(workFlow);

    }

    @Override
    public WorkFlow giveFeedbackToArticle(ReviewerFeedback reviewerFeedback, String articleId) {
        WorkFlow workFlow=this.getWorkFlowByArticleId(articleId);
        if(workFlow==null) throw new NoSuchElementException("Article doesn't exist, kindly verify id");

        ReviewerFeedback reviewerFeedbackFinal=workFlow.getReviewerFeedback();
        reviewerFeedbackFinal.setReviewerUserId(workFlow.getReviewerUserId());
        reviewerFeedbackFinal.setReviewStatus("in-progress");
        reviewerFeedbackFinal.setReviewFeedback(reviewerFeedback.getReviewFeedback());
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        reviewerFeedbackFinal.setReviewTimeStamp(now.format(df).toString());

        workFlow.setReviewerFeedback(reviewerFeedbackFinal);
        workflowRepo.save(workFlow);
        return workFlow;
    }

    @Override
    public WorkFlow editorChangesToArticle(EditorChanges editorChanges, String articleId) {
        WorkFlow workFlow=this.getWorkFlowByArticleId(articleId);
        if(workFlow==null) throw new NoSuchElementException("Article doesn't exist, kindly verify id");
        //ReviewerFeedback reviewerFeedbackFinal=workFlow.getReviewerFeedback();
        EditorChanges editorChangesFinal= new EditorChanges();
        editorChangesFinal.setEditorUserId(workFlow.getEditorUserId());
        editorChangesFinal.setChangedDescription(editorChanges.getChangedDescription());
        editorChangesFinal.setAuthorComment("");

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter df=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        editorChangesFinal.setChangedTimeStamp(now.format(df).toString());

        EditorChanges editorChangesRes=editorChangesRepo.save(editorChangesFinal);
        workFlow.getEditorChangesList().add(editorChangesRes);
        workflowRepo.save(workFlow);
        return workFlow;
    }
}

package com.mcgill.mymuseum.dto;

import com.mcgill.mymuseum.model.Artifact;

public class ArtifactDTO {
    public final Long id;
    public final String name;
    public final String description;
    public final String url;
    public final Long loanId;
    public final Long roomId;

    /**
     * constructor for artifact
     * @param artifact
     */
    public ArtifactDTO(Artifact artifact){
        this.id = artifact.getArtifactId();
        this.name = artifact.getName();
        this.description = artifact.getDescription();
        this.url = artifact.getUrl();
        if (artifact.hasLoan()) {
            this.loanId = artifact.getLoan().getLoanId();
        }else{
            this.loanId = null;
        }
        if (artifact.getRoom() == null){
            this.roomId = null;
        } else {
            this.roomId = artifact.getRoom().getRoomId();
        }
    }
}

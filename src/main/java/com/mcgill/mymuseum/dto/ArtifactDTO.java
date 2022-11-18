package com.mcgill.mymuseum.dto;


import com.mcgill.mymuseum.model.Artifact;

public class ArtifactDTO {
    public final Long id;
    public final String name;
    public final String description;
    public final String url;
    public final Long loanId;
    public final Long roomId;

    public ArtifactDTO(Artifact artifact){
        this.id = artifact.getArtifactId();
        this.name = artifact.getName();
        this.description = artifact.getDescription();
        this.url = artifact.getUrl();
        this.loanId = artifact.getLoan().getLoanId();
        this.roomId = artifact.getRoom().getRoomId();
    }
}

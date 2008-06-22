
DROP TABLE public.following_members;
DROP TABLE public.reply_comments;
DROP TABLE public.favorite_comments;
DROP TABLE public.member_comments;
DROP TABLE public.comment;
DROP TABLE public.member;

CREATE TABLE public.member (
       member_id SERIAL NOT NULL
     , member_name VARCHAR(15) NOT NULL CONSTRAINT UQ_member_name UNIQUE
     , full_name VARCHAR(40) NOT NULL
     , password VARCHAR(20) NOT NULL
     , email VARCHAR(50) NOT NULL
     , locale VARCHAR(20) NOT NULL
     , open BOOL NOT NULL
     , web VARCHAR(100)
     , biography VARCHAR(200)
     , location VARCHAR(100)
     , small_picture BYTEA
     , medium_picture BYTEA
     , large_picture BYTEA
     , PRIMARY KEY (member_id)
);

CREATE TABLE public.comment (
       comment_id SERIAL NOT NULL
     , comment VARCHAR(140) NOT NULL
     , post_time TIMESTAMP NOT NULL
     , PRIMARY KEY (comment_id)
);

CREATE TABLE public.member_comments (
       member_id INT NOT NULL
     , comment_id INT NOT NULL
     , PRIMARY KEY (member_id, comment_id)
     , CONSTRAINT FK_member_comments_1 FOREIGN KEY (member_id)
                  REFERENCES public.member (member_id)
     , CONSTRAINT FK_member_comments_2 FOREIGN KEY (comment_id)
                  REFERENCES public.comment (comment_id)
);

CREATE TABLE public.favorite_comments (
       member_id INT NOT NULL
     , comment_id INT NOT NULL
     , PRIMARY KEY (member_id, comment_id)
     , CONSTRAINT FK_favorite_comments_1 FOREIGN KEY (member_id)
                  REFERENCES public.member (member_id)
     , CONSTRAINT FK_favorite_comments_2 FOREIGN KEY (comment_id)
                  REFERENCES public.comment (comment_id)
);

CREATE TABLE public.reply_comments (
       member_id INT NOT NULL
     , comment_id INT NOT NULL
     , PRIMARY KEY (member_id, comment_id)
     , CONSTRAINT FK_reply_comments_1 FOREIGN KEY (member_id)
                  REFERENCES public.member (member_id)
     , CONSTRAINT FK_reply_comments_2 FOREIGN KEY (comment_id)
                  REFERENCES public.comment (comment_id)
);

CREATE TABLE public.following_members (
       member_id INT NOT NULL
     , following_member_id INT NOT NULL
     , request BOOL NOT NULL
     , PRIMARY KEY (member_id, following_member_id)
     , CONSTRAINT FK_member_id FOREIGN KEY (member_id)
                  REFERENCES public.member (member_id)
     , CONSTRAINT FK_following_member_id FOREIGN KEY (following_member_id)
                  REFERENCES public.member (member_id)
);


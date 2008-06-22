SELECT 
c.comment_id, c.comment, c.post_time 
/*IF userMemberId > 0*/
, (fc.member_id IS NOT NULL) as favorite
/*END*/
FROM comment_view c
/*IF userMemberId > 0*/
LEFT OUTER JOIN favorite_comments fc
ON fc.member_id = /*userMemberId*/42
AND fc.comment_id = c.comment_id
/*END*/
WHERE c.member_id = /*memberId*/1
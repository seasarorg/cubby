SELECT c.* 
/*IF userMemberId > 0*/
, (fc.member_id IS NOT NULL) as favorite
/*END*/
FROM comment_view c
/*IF userMemberId > 0*/
LEFT OUTER JOIN favorite_comments fc
ON fc.member_id = /*userMemberId*/42
AND fc.comment_id = c.comment_id
/*END*/
WHERE c.open = true 
ORDER BY c.comment_id DESC
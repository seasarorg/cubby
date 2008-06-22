SELECT c.*, (fc.member_id IS NOT NULL) as favorite 
FROM comment_view c 
LEFT OUTER JOIN favorite_comments fc
ON fc.member_id = /*userMemberId*/42
AND fc.comment_id = c.comment_id
WHERE (c.member_id IN(
SELECT following_member_id 
FROM following_members
WHERE member_id = /*userMemberId*/42
AND request = false) 
OR c.member_id = /*userMemberId*/42)
